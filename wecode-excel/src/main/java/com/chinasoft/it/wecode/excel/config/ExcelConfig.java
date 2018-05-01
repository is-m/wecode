package com.chinasoft.it.wecode.excel.config;

import javax.inject.Named;

/**
 * Excel 全局配置
 * @author Administrator
 *
 */
@Named
public class ExcelConfig {

	/**
	 * 模版路径
	 */
	private String templatePath = "/appdata/excel/export/templates";

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	
}
