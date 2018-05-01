package com.chinasoft.it.wecode.excel.service;

import java.io.InputStream;

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
	void imports(String serviceKey,InputStream datafile) throws Exception;

}
