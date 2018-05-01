package com.chinasoft.it.wecode.excel.template.context.impl;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.chinasoft.it.wecode.excel.template.context.TemplateRow;
import com.chinasoft.it.wecode.excel.template.utils.POIUtils;

public class StaticTemplateArea extends AbstractTemplateArea {

	@Override
	public int renderTo(Sheet sheet, int startRow) {
		List<TemplateRow> rows = this.getRows();
		
		for (TemplateRow trow : rows) {
			Row row = sheet.createRow(startRow++);
			POIUtils.copyRow(trow.getSrcRow(), row, true);
		}

		return startRow;
	}

}
