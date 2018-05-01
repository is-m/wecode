package com.chinasoft.it.wecode.excel.template.context.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.chinasoft.it.wecode.excel.template.context.TemplateCell;
import com.chinasoft.it.wecode.excel.template.context.TemplateRow;
import com.chinasoft.it.wecode.excel.template.utils.POIUtils;

public class DefaultTemplateRow implements TemplateRow {

	private List<TemplateCell> cells = new ArrayList<>();

	private int index = -1;

	private Row srcRow;

	@Override
	public void addRows(int size) {

	}

	@Override
	public void addCell(TemplateCell cell) {
		this.cells.add(cell);
	}

	@Override
	public List<TemplateCell> getCells() {
		return cells;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int getIndex() {
		return index;
	}

	public DefaultTemplateRow() {
	}

	public DefaultTemplateRow(int index, Row srcRow) {
		this.index = index;
		this.srcRow = srcRow;
	}

	@Override
	public Row getSrcRow() {
		return srcRow;
	}

	private List<TemplateCell> getDynamicCells() {
		return this.getCells().stream().filter(item -> item instanceof DynamicTemplateCell)
				.collect(Collectors.toList());
	}

	@Override
	public void renderTo(Row row) {
		POIUtils.copyRow(this.getSrcRow(), row, true);
		for (TemplateCell tcell : this.getDynamicCells()) {
			Cell cell = row.getCell(tcell.getIndex());
			tcell.setCellValue(cell);
		}
	}

	@Override
	public boolean isDynamic() {
		return this.getDynamicCells().size() > 0;
	}

}
