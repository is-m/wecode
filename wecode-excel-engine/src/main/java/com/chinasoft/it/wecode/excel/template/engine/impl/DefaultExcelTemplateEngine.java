package com.chinasoft.it.wecode.excel.template.engine.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.chinasoft.it.wecode.excel.template.context.TemplateWorkbook;
import com.chinasoft.it.wecode.excel.template.data.DataProvider;
import com.chinasoft.it.wecode.excel.template.engine.ExcelTemplateEngine;

/**
 * 默认Excel模版引擎
 * 
 * @author Administrator
 *
 */
public class DefaultExcelTemplateEngine implements ExcelTemplateEngine {

	@Override
	public void process(InputStream templateStream, OutputStream os, DataProvider provider)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		// 获取excel文档
		Workbook xssfWorkbook = WorkbookFactory.create(templateStream);
		// 生产模版
		TemplateWorkbook templateWorkbook = TemplateBuilder.build(xssfWorkbook);
		System.out.println("templateWorkbook  " + templateWorkbook);
		Workbook target = new SXSSFWorkbook();
		templateWorkbook.renderTo(target);
		target.write(os);
		target.close();
		xssfWorkbook.close();
	}

}
