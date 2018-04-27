package com.chinasoft.it.wecode.excel.service;

/**
 * Excel 导入服务
 * 
 * @author Administrator
 *
 */
public interface IExcelImportService {

	/**
	 * 导入数据
	 * 
	 * @param consumer
	 *            数据消费者
	 */
	void imports(ExcelDataConfig config, IExcelDataConsumer consumer) throws Exception;

	default void imports(IExcelDataConsumer consumer) throws Exception {
		imports(ExcelDataConfig.DEFAULT(), consumer);
	}

}
