package com.chinasoft.it.wecode.trace.utils;

import java.util.List;
import java.util.Map;

/**
 * 调用栈数据对象
 * 
 * @author Administrator
 *
 */
public class CallStack {

	/**
	 * 线程名
	 */
	private String threadName;

	private Map<String, Object> argumentMap;

	private String result;

	/**
	 * 调用者
	 */
	private String caller;

	/**
	 * 调用链接（包、类、方法）
	 */
	private String link;

	/**
	 * 开始时间（毫秒）
	 */
	private Long timestamp;

	/**
	 * 耗时（毫秒）
	 */
	private Long times;

	/**
	 * 子项
	 */
	private List<CallStack> children;

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	public List<CallStack> getChildren() {
		return children;
	}

	public void setChildren(List<CallStack> children) {
		this.children = children;
	}

	/**
	 * 
	 * 
	 * @param traceStart
	 *            默认为2
	 */
	public CallStack(int traceStart) {
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		setTimestamp(System.currentTimeMillis());
		StackTraceElement caller = traces[traceStart < 0 ? 2 : traceStart];
		setLink(caller.getClassName() + "::" + caller.getMethodName() + " " + caller.getLineNumber());
		setThreadName(Thread.currentThread().getName());
	}

	public CallStack(String caller) {
		this(3);
		setCaller(caller);
	}

	public CallStack(int traceStart, String caller) {
		this(traceStart < 3 ? 3 : traceStart);
		setCaller(caller);
	}

	@Override
	public String toString() {
		return String.format("CallStackDto[caller:%s, link:%s, timestamp:%d, times:%d]", caller, link, timestamp,
				times);
	}

	public static void main(String[] args) {
		System.out.println(new CallStack("123"));
	}

}
