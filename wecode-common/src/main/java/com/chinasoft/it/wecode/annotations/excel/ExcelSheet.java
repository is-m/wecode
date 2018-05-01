package com.chinasoft.it.wecode.annotations.excel;

public @interface ExcelSheet {

	/**
	 * 默认取值字段，SheetName，默认值为Sheet1
	 * @return
	 */
	String value() default "Sheet1"; 
	
}
