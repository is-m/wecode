package com.chinasoft.it.wecode.excel.service;

import java.io.OutputStream;

/**
 * Excel 导出服务
 * 
 * @author Administrator
 *
 */
public interface IExcelExportService {

	/**
	 * 导出数据
	 * 
	 * @param dataProvider
	 *            数据提供者
	 * @param output
	 *            输出流
	 */
	void export(ExcelDataConfig config, IExcelDataProvider provider, OutputStream output) throws Exception;

	default void export(IExcelDataProvider provider, OutputStream output) throws Exception {
		export(ExcelDataConfig.DEFAULT(), provider, output);
	}

}
