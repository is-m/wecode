package com.chinasoft.it.wecode.excel.service;

import java.util.List;

/**
 * 数据消费者接口
 * 
 * @author Administrator
 *
 */
@FunctionalInterface
public interface IExcelDataConsumer {

	/**
	 * 消费数据
	 * 
	 * @param config
	 * @param data
	 * @throws Exception
	 */
	void consume(ExcelDataConfig config, List<Object> data) throws Exception;
}
