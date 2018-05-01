package com.chinasoft.it.wecode.excel.template.data;

/**
 * 数据提供程序
 * @author Administrator
 *
 */
@FunctionalInterface
public interface DataProvider {

	/**
	 * 
	 * @return
	 */
	Object produce(DataConfig config);
	
}
