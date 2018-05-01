package com.chinasoft.it.wecode.excel.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.inject.Named;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.excel.service.ExcelServiceContext;
import com.chinasoft.it.wecode.excel.service.IExcelDataProvider;
import com.chinasoft.it.wecode.excel.service.IExcelExportService;

/**
 * 
 * 大数据量导出
 * http://www.cnblogs.com/cheergo/articles/8883866.html
 * @author Administrator
 *
 */
@Named
public class ExcelExportService implements IExcelExportService {

	private static final Logger logger = LoggerFactory.getLogger(ExcelExportService.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void export(String serviceKey, Object condition, OutputStream output) throws Exception {
		logger.info("export excel for serviceKey {} and condition {}", serviceKey, condition);
		IExcelDataProvider provider = ApplicationUtils.getBean(IExcelDataProvider.class, "excel." + serviceKey);
		Map<String, Object> varMap = provider.produce(condition);

		logger.info("export data context {}", varMap);
		if (!ExcelServiceContext.containsExportTemplateKey(serviceKey)) {
			String classfile = "/config/excel/" + serviceKey + ".export.xlsx";
			logger.info("load template for class path file {} ", classfile);
			ExcelServiceContext.putExportTemplate(serviceKey, getClass().getResourceAsStream(classfile));
		}

		InputStream templateStream = ExcelServiceContext.getExportTemplate(serviceKey);
		logger.info("build and output excel file for template ");
		JxlsHelper.getInstance().processTemplate(templateStream, output, new Context(varMap));
		templateStream.close();
	}

}
