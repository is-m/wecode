package com.chinasoft.it.wecode.excel.template.context.impl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.poi.ss.usermodel.Cell;

import com.chinasoft.it.wecode.excel.template.context.TemplateCell;

public abstract class AbstractTemplateCell implements TemplateCell {

	protected int index = -1;

	protected Cell src;

	public AbstractTemplateCell(int index) {
		this.index = index;
	}

	public AbstractTemplateCell(int index, Cell src) {
		this.index = index;
		this.src = src;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public Cell getSrc() {
		return src;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
