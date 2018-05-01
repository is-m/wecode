package com.chinasoft.it.wecode.excel.service;

import java.util.Map;

/**
 * 数据生产者接口
 * 
 * @author Administrator
 *
 */
@FunctionalInterface
public interface IExcelDataProvider<T> {

	/**
	 * 生产数据
	 * 
	 * @return 建议返回List<ObjectDto>
	 */
	Map<String, Object> produce(T condition) throws Exception;

}
