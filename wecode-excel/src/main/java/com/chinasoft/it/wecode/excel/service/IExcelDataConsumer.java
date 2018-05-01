package com.chinasoft.it.wecode.excel.service;

import java.util.Map;

/**
 * 数据消费者接口
 * 
 * @author Administrator
 *
 */
public interface IExcelDataConsumer {

	/**
	 * 消费数据，根据beansMap的提供的keyValue消费 TODO:后续还需要支持返回消费结果，供系统处理，比如说是全部成功还是部分成功，还是全部失败
	 * 
	 * @param data
	 * @throws Exception
	 */
	void consume(Map<String, Object> data) throws Exception;

	/**
	 * 数据容器，需要指定key以及value的具体内容 例如： key:users , value = new
	 * ArrayList<UserResultDto>();
	 * 
	 * @return
	 */
	Map<String, Object> beansMap();
}
