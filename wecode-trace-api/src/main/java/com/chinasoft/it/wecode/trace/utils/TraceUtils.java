package com.chinasoft.it.wecode.trace.utils;

import com.chinasoft.it.wecode.trace.spi.service.TraceService;
import com.chinasoft.it.wecode.trace.spi.service.impl.ConsoleTraceService;

/**
 * 
 * @author Administrator
 *
 */
public class TraceUtils {

	private static CallerResolver callerResolver;

	/**
	 * TODO:待实现动态替换
	 */
	private static TraceService traceService = new ConsoleTraceService();

	public static void initCallerResolver(CallerResolver resolver) {
		callerResolver = resolver;
	}

	public static void log() {
		CallStack callStack = new CallStack(4, callerResolver != null ? callerResolver.getCaller() : "");
		traceService.push(callStack);
	}

	public static void main(String[] args) {
		log();
	}
}
