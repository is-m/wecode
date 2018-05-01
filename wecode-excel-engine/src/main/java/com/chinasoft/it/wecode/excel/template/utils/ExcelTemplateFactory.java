package com.chinasoft.it.wecode.excel.template.utils;

import com.chinasoft.it.wecode.excel.template.engine.ExcelTemplateEngine;
import com.chinasoft.it.wecode.excel.template.engine.impl.DefaultExcelTemplateEngine;

public class ExcelTemplateFactory {

	private static final ExcelTemplateEngine engine = new DefaultExcelTemplateEngine();

	public static ExcelTemplateEngine getEngine() {
		return engine;
	}
}
