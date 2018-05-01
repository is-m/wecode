package com.chinasoft.it.wecode.excel.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IExcelService {

	void imports(String serviceKey, InputStream excelFileInputStream) throws Exception;

	void export(String serviceKey, Object condition, OutputStream output) throws Exception;

	void downloadTemplate(String serviceKey, OutputStream os) throws IOException;

}
