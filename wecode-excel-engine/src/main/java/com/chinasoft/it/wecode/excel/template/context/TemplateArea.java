package com.chinasoft.it.wecode.excel.template.context;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 模版区域
 * 
 * @author Administrator
 *
 */
public interface TemplateArea {

	/**
	 * 获取行
	 * 
	 * @return
	 */
	List<TemplateRow> getRows();

	void addRow(TemplateRow row);

	/**
	 * 渲染到表格
	 * 
	 * @param sheet
	 *            需要渲染的sheet页
	 * @param startRow
	 *            渲染起始行
	 * @return 最后渲染完的位置
	 */
	int renderTo(Sheet sheet, int startRow);
}
