package com.chinasoft.it.wecode.excel.template.utils;

import org.apache.poi.ss.usermodel.CellType;

public class ValuePair {

	private CellType type;

	private Object value;

	public CellType getType() {
		return type;
	}

	public void setType(CellType type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ValuePair() {
	}

	public ValuePair(CellType type, Object value) {
		this.type = type;
		this.value = value;
	}
}
