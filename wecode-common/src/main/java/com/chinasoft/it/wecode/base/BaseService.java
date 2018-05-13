package com.chinasoft.it.wecode.base;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.groups.Default;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import com.chinasoft.it.wecode.common.dto.BaseDto;
import com.chinasoft.it.wecode.common.exception.NoImplementedException;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.common.validation.groups.Create;
import com.chinasoft.it.wecode.common.validation.groups.Query;
import com.chinasoft.it.wecode.common.validation.groups.Update;

/**
 * 服务类父类
 * 
 * TODO:目前校验方法已经失效，需要重新看看
 * 
 * @author Administrator
 *
 * @param <E>
 *            Entity
 * @param <D>
 *            SimpleDto
 * @param <R>
 *            ResultDto
 */
public abstract class BaseService<E extends BaseEntity, D extends BaseDto, R extends BaseDto> {

	/**
	 * logger
	 */
	protected final Logger logger;

	/**
	 * entiyu class 
	 */
	private final Class<E> entityClass;

	/**
	 * repository
	 */
	protected final JpaRepository<E, String> repo;

	/**
	 * dto entity mapper
	 */
	protected final BaseMapper<E, D, R> mapper;

	/**
	 * entity manager
	 * <p>
	 * 可以用来执行更多的SQL相关的API
	 */
	@PersistenceContext
	protected EntityManager em;

	public BaseService(JpaRepository<E, String> repository, BaseMapper<E, D, R> mapper, Class<E> entityClass) {
		this.repo = repository;
		this.mapper = mapper;
		logger = LoggerFactory.getLogger(this.getClass());
		this.entityClass = entityClass;
	}

	/**
	 * 创建对象
	 * 
	 * @param dto
	 * @return
	 */
	public R create(@Validated({ Default.class, Create.class }) D dto) {
		E beforeSave = mapper.to(dto);
		E afterSave = repo.save(beforeSave);
		return mapper.from(afterSave);
	}
	
	/**
	 * 批量创建对象
	 * 
	 * @param dto
	 * @return
	 */
	public List<R> batchCreate(@Validated({ Default.class, Create.class }) List<D> dtos) {
		if(CollectionUtils.isEmpty(dtos)) {
			return null;
		}
		List<E> entities = dtos.stream().map(dto-> mapper.to(dto)).collect(Collectors.toList());
		return repo.save(entities).stream().map(entity -> mapper.from(entity)).collect(Collectors.toList());
	}

	/**
	 * 修改函数，该函数过于凶险，尽可能的少用
	 * 
	 * 后续应该是根据ID查询出来后，考虑：按属性不为空的逐个替换，而非整体使用外部传进来的参数
	 * 
	 * @param id
	 * @param dto
	 * @return
	 */
	public R update(@NotBlank String id, @Validated({ Default.class, Update.class }) D dto) {
		E beforeSave = mapper.to(dto);
		((BaseEntity) beforeSave).setId(id);
		E afterSave = repo.save(beforeSave);
		return mapper.from(afterSave);
	}

	/**
	 * 根据ID获取对象
	 * 
	 * @param id
	 * @return
	 */
	public R findOne(@NotBlank String id) {
		E entity = repo.findOne(id);
		return mapper.from(entity);
	}

	/**
	 * 查询 对象 (ID In List)
	 * 
	 * @param ids
	 * @return
	 */
	public List<R> findAll(Iterable<String> ids) {
		List<E> list = repo.findAll(ids);
		return mapper.toDtoList(list);
	}

	/**
	 * 分页查询 (如果存在模糊查询，则需要实现Special接口) findByUseridLikeOrUserNameLike
	 * 如果like不生效，可以使用Containing findByIpContaining
	 * 
	 * @param pageable
	 * @param queryDto
	 * @return
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public Page<R> findPagedList(Pageable pageable, @Validated({ Query.class }) BaseDto queryDto) {
		// 检查Dto是否存在模糊查询的情况(标注了模糊查询的字段)，如果存在则需要是有 Specification 的接口
		// TODO 待实现动态查询细节
		/*Page<E> pageData = null;

		if (true) {
			pageData = repo.findAll(pageable);
		} else if (repo instanceof JpaSpecificationExecutor) {
			pageData = ((JpaSpecificationExecutor<E>) repo).findAll(new Specification<E>() {
				@Override
				public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate fieldLikes = cb.like(root.get("fieldName").as(String.class), "%" + "likeValue" + "%");
					query.where(cb.and(fieldLikes));
					return query.getRestriction();
				}
			}, pageable);
		} else {
			throw new NoImplementedException("存在模糊查询条件，但是没有 Repository 未实现 JpaSpecificationExecutor 接口");
		}

		return mapper.toResultDto(pageData);*/
		return null;
	}

	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @return
	 */
	public void delete(@NotBlank String id) {
		repo.delete(id);
	}

	// JPA
	// https://www.cnblogs.com/fengru/p/5922793.html?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
	/**
	 * 根据ID删除 
	 * TODO:删除时，jpa有进行过一次查询后再进行的删除,而且是执行的多条删除语句，需要看看是否需要优化
	 * TODO:未关联删除，需要人为的去找到关联删除项
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public void delete(String... ids) {
		if (ArrayUtils.isEmpty(ids)) {
			logger.warn("delete id collection is empty.");
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];

			if (StringUtils.isEmpty(id)) {
				logger.warn("delete operate has empty id.");
				continue;
			}

			E entity = repo.findOne(id);
			if(entity == null) {
				logger.warn("delete skip id is {} ,not found this record",id);
				continue;
			}
			
			repo.delete(entity);
			
			// 在进行大批量数据一次性操作的时候，会占用非常多的内存来缓存被更新的对象。这时就应该阶段性地调用clear()方法来清空一级缓存中的对象，控制一级缓存的大小，以避免产生内存溢出的情况。
			/*	if (i % 30 == 0) {
				em.flush();
				em.clear();
			}*/
		}
	}
	
	public List<R> save(List<R> dtos) {
		List<E> entities = mapper.toEntities(dtos);
		return mapper.toDtoList(repo.save(entities));
	}

	/**
	 * 构造实体对象
	 * @param id
	 * @return
	 */
	protected E newEntity(String id) {
		try {
			E result = entityClass.newInstance();
			result.setId(id);
			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("instance entity class fiald " + entityClass.getName(), e);
		}
	}

	/**
	 * 构造实体对象
	 * @return
	 */
	protected E newEntity() {
		return newEntity(null);
	}
}
