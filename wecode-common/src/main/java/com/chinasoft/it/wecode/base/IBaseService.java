package com.chinasoft.it.wecode.base;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.groups.Default;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.chinasoft.it.wecode.annotations.security.Operate;
import com.chinasoft.it.wecode.common.dto.BaseDto;
import com.chinasoft.it.wecode.common.validation.groups.Create;
import com.chinasoft.it.wecode.common.validation.groups.Query;
import com.chinasoft.it.wecode.common.validation.groups.Update;
import com.chinasoft.it.wecode.fw.spring.support.BeanValidationConfiguration;

/**
 * 基础服务接口
 * 
 * 已经融合了Spring method validation
 * @Validated 需要将 MethodValidationPostProcessor 开启后方可生效（xml or bean config ）
 * @see BeanValidationConfiguration
 * @author Administrator
 *
 * @param <D> 数据对象
 * @param <R> 返回结果对象
 */
@Validated
public interface IBaseService<D extends BaseDto, R extends BaseDto> {

  /**
   * 创建对象
   * 
   * @param dto
   * @return
   */
  @Validated({Default.class, Create.class})
  @Operate(code = "create", desc = "create")
  R create(@Valid D dto);

  /**
   * 批量创建对象
   * 
   * @param dto
   * @return
   */
  @Validated({Default.class, Create.class})
  List<R> batchCreate(@Valid List<D> dtos);

  /**
   * 修改函数，该函数过于凶险，尽可能的少用
   * 
   * 后续应该是根据ID查询出来后，考虑：按属性不为空的逐个替换，而非整体使用外部传进来的参数
   * 
   * @param id
   * @param dto
   * @return
   */
  @Validated({Default.class, Update.class})
  R update(@NotBlank String id, @Valid D dto);

  /**
   * 根据ID获取对象
   * 
   * @param id
   * @return
   */
  R findOne(@NotBlank String id);

  /**
   * 查询 对象 (ID In List)
   * 
   * @param ids
   * @return
   */
  List<R> findAll(Iterable<String> ids);

  /**
   * 分页查询 (如果存在非Equals类型的查询条件是，如果要使用该接口，repository 必须实现 JpaSpecificationExecutor
   * 接口) findByUseridLikeOrUserNameLike 如果like不生效，可以使用Containing
   * findByIpContaining
   * 
   * @param pageable
   * @param queryDto
   * @return
   */
  Page<R> findPagedList(Pageable pageable, @Validated({Query.class}) BaseDto queryDto);

  /**
   * 根据ID删除
   * 
   * @param id
   * @return
   */
  void delete(@NotBlank String id);

  // JPA
  // https://www.cnblogs.com/fengru/p/5922793.html?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
  /**
   * 根据ID删除 TODO:删除时，jpa有进行过一次查询后再进行的删除,而且是执行的多条删除语句，需要看看是否需要优化
   * TODO:未关联删除，需要人为的去找到关联删除项
   * 
   * @param id
   * @return
   */
  @Validated
  void delete(@NotEmpty String... ids);

  /**
   * 批量保存
   * @param dtos
   * @return
   */
  List<R> save(List<R> dtos);

}
