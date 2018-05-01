package com.chinasoft.it.wecode.excel.template.context;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;

public interface TemplateRow {
	int getIndex();

	void addRows(int size);

	/**
	 * 添加模版单元格
	 * 
	 * @param cell
	 */
	void addCell(TemplateCell cell);

	/**
	 * 获取模版单元格
	 * 
	 * @return
	 */
	List<TemplateCell> getCells();

	Row getSrcRow();
	
	void renderTo(Row row);
	
	/**
	 * 是否为动态行
	 * @return
	 */
	boolean isDynamic();
}
