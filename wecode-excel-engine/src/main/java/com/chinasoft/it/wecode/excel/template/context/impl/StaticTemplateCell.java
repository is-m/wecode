package com.chinasoft.it.wecode.excel.template.context.impl;

import org.apache.poi.ss.usermodel.Cell;

import com.chinasoft.it.wecode.excel.template.utils.POIUtils;

public class StaticTemplateCell extends AbstractTemplateCell {

	public StaticTemplateCell(int index) {
		super(index);
	}

	public StaticTemplateCell(int index, Cell src) {
		super(index, src);
	}

	@Override
	public void setCellValue(Cell cell) {
		POIUtils.copyCellValue(this.src, cell);
	}

}
