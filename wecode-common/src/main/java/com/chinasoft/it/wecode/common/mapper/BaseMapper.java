package com.chinasoft.it.wecode.common.mapper;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ReflectionUtils;

import com.chinasoft.it.wecode.common.exception.NoImplementedException;
import com.chinasoft.it.wecode.common.util.PageConstants;

/**
 * 转换基础接口
 * 
 * @author Administrator
 *
 * @param <E>
 *            Entity
 * @param <D>
 *            FormDto
 * @param <R>
 *            ResultDto
 */
public interface BaseMapper<E, D, R> {

	/**
	 * Entity 转 Result Dto
	 * 
	 * @param dto
	 * @return
	 */
	R from(E entity);

	/**
	 * List Entity 转 List Result Dto
	 * 
	 * @param e
	 * @return
	 */
	List<R> toDtoList(List<E> e);

	/**
	 * DTO 转 Entity
	 * 
	 * @param dto
	 * @return
	 */
	E to(D dto);
	
	E result2Entity(R dto);

	/**
	 * Result Dto List 转 Entity List
	 * 
	 * @param r
	 * @return
	 */
	List<E> toEntities(List<R> dtos);

	/**
	 * List DTO 转 List Entity
	 * 
	 * @param dto
	 * @return
	 */
	List<E> toEntityList(List<D> dto);

	/**
	 * 分页实体数据 转 分页Dto数据
	 * 
	 * @param entities
	 * @return
	 */
	default Page<R> toResultDto(Page<E> entities) {
		Page<R> allocate = entities.map(new Converter<E, R>() {
			public R convert(E source) {
				return from(source);
			};
		});
		// 如果系统设置的参数配置的起始页为1，则需要修改page的起始页
		if (PageConstants.START_PAGE == 1) {
			int curPage = allocate.getNumber() + 1;
			if (allocate instanceof PageImpl) {
				Field pageableField = ReflectionUtils.findField(PageImpl.class, "pageable");
				pageableField.setAccessible(true);
				Pageable pageable = (Pageable) ReflectionUtils.getField(pageableField, allocate);
				Field pageField = ReflectionUtils.findField(AbstractPageRequest.class, "page");
				pageField.setAccessible(true);
				ReflectionUtils.setField(pageField, pageable, curPage);
			} else {
				throw new NoImplementedException("使用了未知的Pageable实现，重新补充此位置的重置当前页逻辑");
			}
		}
		return allocate;
	}
}
