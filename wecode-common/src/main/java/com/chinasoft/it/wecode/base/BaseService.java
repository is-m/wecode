package com.chinasoft.it.wecode.base;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
 * @author Administrator
 *
 * @param <E>
 * @param <D>
 * @param <R>
 */
public abstract class BaseService<E extends BaseEntity, D extends BaseDto, R extends BaseDto> {

	protected final JpaRepository<E, String> repo;

	protected final BaseMapper<E, D, R> mapper;

	public BaseService(JpaRepository<E, String> repository, BaseMapper<E, D, R> mapper) {
		this.repo = repository;
		this.mapper = mapper;
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
	@SuppressWarnings("unchecked")
	public Page<R> findPagedList(Pageable pageable, @Validated({ Query.class }) BaseDto queryDto) {
		// 检查Dto是否存在模糊查询的情况(标注了模糊查询的字段)，如果存在则需要是有 Specification 的接口
		// TODO 待实现动态查询细节
		Page<E> pageData = null;
		
		if(true) {
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

		return mapper.toResultDto(pageData);
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
}
