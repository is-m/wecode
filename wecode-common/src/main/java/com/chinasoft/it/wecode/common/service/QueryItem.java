package com.chinasoft.it.wecode.common.service;

import com.chinasoft.it.wecode.common.service.Query.Type;

public class QueryItem {

	/**
	 * 查询字段
	 */
	private String field;

	/**
	 * 查询类型
	 */
	private Type type;

	/**
	 * 值
	 */
	private Object value;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public QueryItem() {
	}

	public QueryItem(String field, Type type, Object value) {
		this.field = field;
		this.type = type;
		this.value = value;
	}

}
