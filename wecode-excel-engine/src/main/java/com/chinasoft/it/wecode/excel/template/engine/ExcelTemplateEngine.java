package com.chinasoft.it.wecode.excel.template.engine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.chinasoft.it.wecode.excel.template.data.DataProvider;

/**
 * 模版引擎
 * @author Administrator
 *
 */
public interface ExcelTemplateEngine {

	/**
	 * 模版处理接口
	 * @param templateInputStream
	 * @param output
	 * @param provider
	 * @throws IOException
	 */
	void process(InputStream templateInputStream, OutputStream output, DataProvider provider) throws IOException, EncryptedDocumentException, InvalidFormatException;
}
