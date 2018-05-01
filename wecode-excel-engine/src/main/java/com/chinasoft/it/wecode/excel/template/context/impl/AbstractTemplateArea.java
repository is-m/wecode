package com.chinasoft.it.wecode.excel.template.context.impl;

import java.util.ArrayList;
import java.util.List;

import com.chinasoft.it.wecode.excel.template.context.TemplateArea;
import com.chinasoft.it.wecode.excel.template.context.TemplateRow;

public abstract class AbstractTemplateArea implements TemplateArea {

	private List<TemplateRow> rows = new ArrayList<>();

	@Override
	public List<TemplateRow> getRows() {
		return rows;
	}

	@Override
	public void addRow(TemplateRow row) {
		this.rows.add(row);
	}
}
