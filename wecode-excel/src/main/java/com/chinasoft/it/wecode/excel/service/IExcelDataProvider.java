package com.chinasoft.it.wecode.excel.service;

/**
 * 数据生产者接口
 * 
 * @author Administrator
 *
 */
@FunctionalInterface
public interface IExcelDataProvider {

	/**
	 * 生产数据
	 * 
	 * @return 建议返回List<ObjectDto>
	 */
	Object produce(ExcelDataConfig config) throws Exception;

}
