package com.chinasoft.it.wecode.excel.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.util.StreamUtils;

import com.chinasoft.it.wecode.excel.service.IExcelExportService;
import com.chinasoft.it.wecode.excel.service.IExcelImportService;
import com.chinasoft.it.wecode.excel.service.IExcelService;

@Named
public class ExcelService implements IExcelService {

	@Inject
	private IExcelExportService exportService;

	@Inject
	private IExcelImportService importService;

	@Override
	public void imports(String serviceKey, InputStream datafile) throws Exception {
		importService.imports(serviceKey, datafile);
	}

	@Override
	public void export(String serviceKey, Object condition, OutputStream output) throws Exception {
		exportService.export(serviceKey, condition, output);
	}

	/**
	 * TODO:考虑是否需要缓存
	 */
	@Override
	public void downloadTemplate(String serviceKey, OutputStream os) throws IOException {
		InputStream is = getClass().getResourceAsStream("/config/excel/" + serviceKey + ".template.xlsx");
		Objects.requireNonNull(is, "not found template file for service key " + serviceKey);
		StreamUtils.copy(is, os);
	}

}
