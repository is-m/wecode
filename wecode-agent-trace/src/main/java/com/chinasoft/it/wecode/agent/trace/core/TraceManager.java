package com.chinasoft.it.wecode.agent.trace.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 链路跟踪管理类
 * 
 * @author Administrator
 *
 */
public class TraceManager {

	private static ThreadLocal<String> id = new ThreadLocal<>();

	private static Map<String, TraceNode> traceMap = new HashMap<>();

	/**
	 * 开始链路
	 */
	public static void begin() {
		String traceId = UUID.randomUUID().toString().replaceAll("-", "");
		id.set(traceId);
		System.out.println("[INFO] trace begin for id " + traceId);
	}

	/**
	 * 开始追踪
	 * 
	 * @param paramNames
	 * @param args
	 */
	public static void logStart(String[] paramNames, Object[] args) {
		String traceId = id.get();
		if (traceId == null) {
			begin();
			traceId = id.get();
		}

		Map<String, Object> argumentMap = new HashMap<>();
		for (int i = 0; i < paramNames.length; i++) {
			argumentMap.put(paramNames[i], args[i + 1]);
		}

		TraceNode node = new TraceNode(traceId);
		node.setArgumentMap(argumentMap);

		if (traceMap.containsKey(traceId)) {

		}
	}

	/**
	 * 结束追踪
	 */
	public static void logEnd() {
		TraceNode node = null;
		node.setTimes(System.currentTimeMillis() - node.getTimestamp());
		System.out.println("TRACE:" + node);
	}

	/**
	 * 结束链路
	 */
	public static void end() {
		// 打印并输出日志
		String traceId = id.get();
		if (traceId != null) {
			System.out.println("[INFO] trace end for id " + traceId);
			id.remove();
		} else {
			System.out.println("[WANING] trace end but id not initialized.");
		}
	}

}
