package com.chinasoft.it.wecode.excel.template.context;

import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;

public interface TemplateSheet {

	int maxRows();

	int maxColumns();

	/**
	 * Sheet Name
	 * 
	 * @return
	 */
	String getSheetName();

	/**
	 * Sheet Index
	 * 
	 * @return
	 */
	int getIndex();

	List<TemplateArea> getAreas();

	void addArea(TemplateArea area);

	/**
	 * 获取所有模版行
	 * 
	 * @return
	 */
	List<TemplateRow> getRows();

	/**
	 * 添加模版行
	 * 
	 * @param row
	 */
	void addRow(TemplateRow row);

	/**
	 * 添加模版所需数据key
	 * 
	 * @param dataKey
	 */
	void addDataKey(String key);

	/**
	 * 当前当前Sheet所需模版数据列表
	 * 
	 * @return
	 */
	Set<String> getDataKeys();

	Sheet getSrcSheet();

	/**
	 * 渲染到sheet
	 * 
	 * @param sheet
	 */
	void renderTo(Sheet sheet);

}
