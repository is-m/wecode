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
	 * @param serviceKey
	 *            数据提供程序服务KEY（取值为服务名称或者）
	 * @param condition
	 *            导出条件
	 * @param output
	 *            输出流
	 * @throws Exception
	 */
	void export(String serviceKey, Object condition, OutputStream output) throws Exception;

	/**
	 * 导出数据
	 * 
	 * @param serviceKey
	 *            数据提供程序服务KEY（取值为服务名称或者）
	 * @param condition
	 *            导出条件
	 * @param templateClass
	 *            自动模版生成类（需要标记annotations.excel中的注解才能识别）
	 * @param output
	 *            输出流
	 * @throws Exception
	 */
	default void export(String serviceKey, Object condition, Class<?> templateClass, OutputStream output)
			throws Exception {
		// 解析模版类
		System.out.println("准备解析导出模版类,并放到导出上下文");
		// 注意并发问题
		if (!ExcelServiceContext.containsExportTemplateKey(serviceKey)) {
			synchronized (IExcelExportService.class) {
				if (!ExcelServiceContext.containsExportTemplateKey(serviceKey)) {
					System.out.println("解析模版");
				}
			}
		}
		export(serviceKey, condition, output);
	}
}
