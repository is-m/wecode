package com.chinasoft.it.wecode.trace.utils;

@FunctionalInterface
public interface CallerResolver {

	/**
	 * 获取调用者信息
	 * 
	 * @return
	 */
	String getCaller();

}
