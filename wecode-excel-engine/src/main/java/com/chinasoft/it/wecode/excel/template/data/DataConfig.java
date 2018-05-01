package com.chinasoft.it.wecode.excel.template.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DataConfig {

	/**
	 * 属性名
	 */
	private String key;

	/**
	 * 总记录数
	 */
	private int total = 0;

	/**
	 * 页大小
	 */
	private int size = 200;

	/**
	 * 当前页
	 */
	private int current = 0;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public boolean next() {
		return total > 0 && (current++ * size) < total;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public DataConfig() {
	}
}
