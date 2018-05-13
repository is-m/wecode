package com.chinasoft.it.wecode.excel.service;

import java.io.InputStream;

/**
 * Excel 导入服务
 * 关于海量数据导入，可以使用POI SAX 文档解析方式
 * https://blog.csdn.net/sxj_world/article/details/74528588
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
