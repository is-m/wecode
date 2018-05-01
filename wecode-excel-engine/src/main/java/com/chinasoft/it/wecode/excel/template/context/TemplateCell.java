package com.chinasoft.it.wecode.excel.template.context;

import org.apache.poi.ss.usermodel.Cell;

public interface TemplateCell {

	/**
	 * 获取单元格索引
	 * 
	 * @return
	 */
	int getIndex();

	Cell getSrc();

	/**
	 * 设置单元格的值
	 * 
	 * @param cell
	 */
	void setCellValue(Cell cell);
}
