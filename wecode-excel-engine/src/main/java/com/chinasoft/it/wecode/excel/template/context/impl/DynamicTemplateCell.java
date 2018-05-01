package com.chinasoft.it.wecode.excel.template.context.impl;

import org.apache.poi.ss.usermodel.Cell;

public class DynamicTemplateCell extends AbstractTemplateCell {

	private String format = "";

	public DynamicTemplateCell(int index) {
		super(index);
	}

	public DynamicTemplateCell(int index, Cell src) {
		super(index, src);
	}

	public DynamicTemplateCell(int index, Cell src, String format) {
		super(index, src);
		this.format = format;
	}

	@Override
	public void setCellValue(Cell cell) {
		cell.setCellValue(this.format);
	}

}
