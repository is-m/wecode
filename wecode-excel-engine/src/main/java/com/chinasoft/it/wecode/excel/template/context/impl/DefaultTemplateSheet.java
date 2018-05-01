package com.chinasoft.it.wecode.excel.template.context.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.poi.ss.usermodel.Sheet;

import com.chinasoft.it.wecode.excel.template.context.TemplateArea;
import com.chinasoft.it.wecode.excel.template.context.TemplateRow;
import com.chinasoft.it.wecode.excel.template.context.TemplateSheet;

public class DefaultTemplateSheet implements TemplateSheet {

	private List<TemplateArea> areas = new ArrayList<>();

	private List<TemplateRow> rows = new ArrayList<>();

	private Set<String> dataKeys = new TreeSet<>();

	private String name;

	private int index = -1;

	private Sheet srcSheet;

	@Override
	public String getSheetName() {
		return name;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public List<TemplateRow> getRows() {
		return rows;
	}

	@Override
	public void addRow(TemplateRow row) {
		rows.add(row);
	}

	@Override
	public void addDataKey(String key) {
		dataKeys.add(key);
	}

	@Override
	public Set<String> getDataKeys() {
		return dataKeys;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public DefaultTemplateSheet() {
	}

	public DefaultTemplateSheet(String name) {
		this.name = name;
	}

	public DefaultTemplateSheet(String name, Sheet srcSheet) {
		this.name = name;
		this.srcSheet = srcSheet;
	}

	@Override
	public Sheet getSrcSheet() {
		return srcSheet;
	}

	@Override
	public int maxRows() {
		return rows.size();
	}

	@Override
	public int maxColumns() {
		return rows.stream().map(row -> row.getCells().size()).max((a, b) -> Integer.max(a, b)).get();
	}

	@Override
	public List<TemplateArea> getAreas() {
		return areas;
	}

	@Override
	public void renderTo(Sheet sheet) {
		// 设置列宽
		for (int i = 0; i < this.maxColumns(); i++) {
			sheet.setColumnWidth(i, this.getSrcSheet().getColumnWidth(i));
		}

		int startPos = 0;
		for (TemplateArea area : areas) {
			startPos = area.renderTo(sheet, startPos) + 1;
		}
	}

	@Override
	public void addArea(TemplateArea area) {
		this.areas.add(area);
	}

}
