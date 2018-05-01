package com.chinasoft.it.wecode.excel.template.context.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.chinasoft.it.wecode.excel.template.context.TemplateSheet;
import com.chinasoft.it.wecode.excel.template.context.TemplateWorkbook;

public class DefaultTemplateWorkbook implements TemplateWorkbook {

	private List<TemplateSheet> sheets = new ArrayList<>();

	private Workbook src;

	@Override
	public List<TemplateSheet> getSheets() {
		return sheets;
	}

	@Override
	public void addSheet(TemplateSheet sheet) {
		sheets.add(sheet);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public void renderTo(Workbook workbook) {
		for (TemplateSheet tsheet : sheets) {
			Sheet sheet = workbook.createSheet(tsheet.getSheetName());
			tsheet.renderTo(sheet);
		}
	}

	@Override
	public Workbook getSrc() {
		return src;
	}

}
