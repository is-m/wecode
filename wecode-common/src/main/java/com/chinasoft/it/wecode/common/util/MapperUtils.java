package com.chinasoft.it.wecode.common.util;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

import com.chinasoft.it.wecode.common.mapper.BaseMapper;

public class MapperUtils {

	private static final Logger logger = LogUtils.getLogger();

	public static <E, D, R> List<R> from(List<E> entities, BaseMapper<E, D, R> mapper) {
		return CollectionUtils.forEach2(entities, item -> mapper.from(item));
	}

	@SuppressWarnings("unused")
	public static <E, D, R> Page<R> from(Page<E> page, BaseMapper<E, D, R> mapper) { 
		// FIXME:如果有需要设置起始页为1，需要实现下面逻辑
		if(PageConstants.START_PAGE == 1) {
			logger.warn("当其实页为1时，暂时未设置查询结果的当前页+1");
		}
		return page.map(new Converter<E, R>() {
			public R convert(E source) {
				return mapper.from(source);
			};
		});
	}
}
