package com.chinasoft.it.wecode.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

	public static Logger getLogger() {
		try {
			StackTraceElement[] stackTrace = new Throwable().getStackTrace();
			String className = stackTrace[1].getClassName();
			return LoggerFactory.getLogger(Class.forName(className));
		} catch (Exception e) {
			throw new RuntimeException("获取日志对象异常", e);
		}

		// StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		// System.out.println(trace[0].getMethodName());
	}

	public static Logger getLogger(Class<?> clz) {
		return LoggerFactory.getLogger(clz);
	}
}
