package com.chinasoft.it.wecode.base;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.chinasoft.it.wecode.annotations.security.Operate;
import com.chinasoft.it.wecode.common.dto.BaseDto;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.common.service.SpringDataUtils;

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
public abstract class BaseService<E extends BaseEntity, D extends BaseDto, R extends BaseDto> implements IBaseService<D, R> {

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
    logger = LoggerFactory.getLogger(this.getClass());
    this.repo = repository;
    this.mapper = mapper;
    this.entityClass = entityClass;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.chinasoft.it.wecode.base.IBaseService#create(D)
   */
  @Override
  @Operate(code = "create", desc = "create")
  public R create(D dto) {
    E beforeSave = mapper.to(dto);
    E afterSave = repo.save(beforeSave);
    return mapper.from(afterSave);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.chinasoft.it.wecode.base.IBaseService#batchCreate(java.util.List)
   */
  @Override
  @Operate(code = "create", desc = "create")
  public List<R> batchCreate(List<D> dtos) {
    if (CollectionUtils.isEmpty(dtos)) {
      return null;
    }
    List<E> entities = dtos.stream().map(dto -> mapper.to(dto)).collect(Collectors.toList());
    return repo.saveAll(entities).stream().map(entity -> mapper.from(entity)).collect(Collectors.toList());
  }


  @Override
  @Operate(code = "update", desc = "update")
  public R update(String id, D dto) {
    E beforeSave = mapper.to(dto);
    ((BaseEntity) beforeSave).setId(id);
    E afterSave = repo.save(beforeSave);
    return mapper.from(afterSave);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.chinasoft.it.wecode.base.IBaseService#findOne(java.lang.String)
   */
  @Override
  @Operate(code = "view", desc = "view")
  public R findOne(String id) {
    E entity = repo.getOne(id);
    return mapper.from(entity);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.chinasoft.it.wecode.base.IBaseService#findAll(java.lang.Iterable)
   */
  @Override
  @Operate(code = "view", desc = "view")
  public List<R> findAll(Iterable<String> ids) {
    List<E> list = repo.findAllById(ids);
    return mapper.toDtoList(list);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.chinasoft.it.wecode.base.IBaseService#findPagedList(org.springframework.data.domain.
   * Pageable, com.chinasoft.it.wecode.common.dto.BaseDto)
   */
  @Override
  @Operate(code = "view", desc = "view")
  public Page<R> findPagedList(Pageable pageable, BaseDto queryDto) {
    Page<E> pageData = SpringDataUtils.findPagedData(repo, pageable, queryDto);
    return mapper.toResultDto(pageData);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.chinasoft.it.wecode.base.IBaseService#delete(java.lang.String)
   */
  @Override
  @Operate(code = "delete", desc = "delete")
  public void delete(String id) {
    repo.deleteById(id);
  }

  // JPA
  // https://www.cnblogs.com/fengru/p/5922793.html?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
  /*
   * (non-Javadoc)
   * 
   * @see com.chinasoft.it.wecode.base.IBaseService#delete(java.lang.String)
   */
  @Override
  @Transactional
  @Operate(code = "delete", desc = "delete")
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

      E entity = repo.getOne(id);
      if (entity == null) {
        logger.warn("delete skip id is {} ,not found this record", id);
        continue;
      }

      repo.delete(entity);

      // 在进行大批量数据一次性操作的时候，会占用非常多的内存来缓存被更新的对象。这时就应该阶段性地调用clear()方法来清空一级缓存中的对象，控制一级缓存的大小，以避免产生内存溢出的情况。
      /*
       * if (i % 30 == 0) { em.flush(); em.clear(); }
       */
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.chinasoft.it.wecode.base.IBaseService#save(java.util.List)
   */
  @Override
  @Operate(code = "create", desc = "create")
  public List<R> save(List<R> dtos) {
    List<E> entities = mapper.toEntities(dtos);
    return mapper.toDtoList(repo.saveAll(entities));
  }

  /**
   * 构造实体对象
   * 
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
   * 
   * @return
   */
  protected E newEntity() {
    return newEntity(null);
  }

  public BaseMapper<E, D, R> getMapper() {
    return this.mapper;
  }

}
