package com.chinasoft.it.wecode.agent.trace.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 追踪节点
 * 
 * @author Administrator
 *
 */
public class TraceNode {

	private String traceId;

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
	 * 父节点
	 */
	private TraceNode parent;

	/**
	 * 子节点
	 */
	private List<TraceNode> children = new ArrayList<>(0);

	public TraceNode getParent() {
		return parent;
	}

	public void setParent(TraceNode parent) {
		this.parent = parent;
	}

	public List<TraceNode> getChildren() {
		return children;
	}

	public void setChildren(List<TraceNode> children) {
		this.children = children;
	}

	public void addChild(TraceNode node) {
		getChildren().add(node);
		if (!this.equals(node.parent)) {
			node.setParent(this);
		}
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public Map<String, Object> getArgumentMap() {
		return argumentMap;
	}

	public void setArgumentMap(Map<String, Object> argumentMap) {
		this.argumentMap = argumentMap;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public TraceNode() {
	}

	public TraceNode(String traceId) {
		this.traceId = traceId;
		setTimestamp(System.currentTimeMillis());
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
