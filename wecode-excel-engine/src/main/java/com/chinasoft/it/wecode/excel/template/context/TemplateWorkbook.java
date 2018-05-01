package com.chinasoft.it.wecode.excel.template.context;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Workbook;

public interface TemplateWorkbook {

	Workbook getSrc();

	/**
	 * 获取模版页
	 * 
	 * @return
	 */
	List<TemplateSheet> getSheets();

	/**
	 * 添加模版页
	 * 
	 * @param sheet
	 */
	void addSheet(TemplateSheet sheet);

	/**
	 * 获取模版工作簿所需数据key集合
	 * 
	 * @return
	 */
	default Set<String> getDataKeys() {
		Set<String> result = new TreeSet<>();
		for (TemplateSheet sheet : getSheets()) {
			result.addAll(sheet.getDataKeys());
		}
		return result;
	}

	void renderTo(Workbook workbook);
}
