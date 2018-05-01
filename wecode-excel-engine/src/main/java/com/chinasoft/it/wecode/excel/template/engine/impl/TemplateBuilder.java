package com.chinasoft.it.wecode.excel.template.engine.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.chinasoft.it.wecode.excel.template.context.TemplateArea;
import com.chinasoft.it.wecode.excel.template.context.TemplateCell;
import com.chinasoft.it.wecode.excel.template.context.TemplateRow;
import com.chinasoft.it.wecode.excel.template.context.TemplateSheet;
import com.chinasoft.it.wecode.excel.template.context.TemplateWorkbook;
import com.chinasoft.it.wecode.excel.template.context.impl.DefaultTemplateRow;
import com.chinasoft.it.wecode.excel.template.context.impl.DefaultTemplateSheet;
import com.chinasoft.it.wecode.excel.template.context.impl.DefaultTemplateWorkbook;
import com.chinasoft.it.wecode.excel.template.context.impl.DynamicTemplateCell;
import com.chinasoft.it.wecode.excel.template.context.impl.StaticTemplateArea;
import com.chinasoft.it.wecode.excel.template.context.impl.StaticTemplateCell;

public class TemplateBuilder {

	public static TemplateWorkbook build(Workbook workbook) {
		int sheetNumbers = workbook.getNumberOfSheets();

		TemplateWorkbook templateWorkbook = new DefaultTemplateWorkbook();

		for (int i = 0; i < sheetNumbers; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			TemplateSheet templateSheet = buildTemplateSheet(sheet, workbook);
			templateWorkbook.addSheet(templateSheet);
		}

		return templateWorkbook;
	}

	private static TemplateSheet buildTemplateSheet(Sheet sheet, Workbook workbook) {
		TemplateSheet templateSheet = new DefaultTemplateSheet(sheet.getSheetName(), sheet);

		int lastRowIndex = sheet.getLastRowNum() + 1;
		for (int ri = 0; ri < lastRowIndex; ri++) {
			Row row = sheet.getRow(ri);
			if (row == null) {
				continue;
			}

			TemplateRow templateRow = new DefaultTemplateRow(ri, row);

			int lastCellIndex = row.getLastCellNum() + 1;
			for (int ci = 0; ci < lastCellIndex; ci++) {
				// 检查单元格内的值是否普通值还是变量值
				Cell cell = row.getCell(ci);

				if (cell == null) {
					continue;
				}

				TemplateCell templateCell = null;
				if (CellType.FORMULA == cell.getCellTypeEnum()) {
					continue;
				}

				String value = cell.getStringCellValue();
				if (value != null) {
					String trimValue = value.trim();
					// 当前cell存在变量的情况，则表示当前行可能是动态行
					if (trimValue.startsWith("${") && trimValue.endsWith("}")) {
						String foramtValue = trimValue.replaceAll("^\\$\\{|\\}$", "");
						DynamicTemplateCell dynamicCell = new DynamicTemplateCell(ci, cell, foramtValue);
						// 如果模版字符串中存在.，则表示需要带key的数据源
						int dotIndex = foramtValue.indexOf(".");
						if (dotIndex > 0) {
							String dataKey = foramtValue.substring(0, dotIndex);
							templateSheet.addDataKey(dataKey);
						} else {
							templateSheet.addDataKey("__global");
						}

						templateCell = dynamicCell;
					} else {
						templateCell = new StaticTemplateCell(ci, cell);
					}
				}

				templateRow.addCell(templateCell);
			}
			templateSheet.addRow(templateRow);
		}

		/**
		 * TODO:未实现了
		 */
		TemplateArea templateArea = new StaticTemplateArea();
		for (TemplateRow row : templateSheet.getRows()) {
			templateArea.addRow(row);
		}
		templateSheet.addArea(templateArea);

		return templateSheet;
	}
}
