package com.chinasoft.it.wecode.common.mapper;

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
	 * DTO 转 Entity
	 * 
	 * @param dto
	 * @return
	 */
	E to(D dto);
}
