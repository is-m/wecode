package com.chinasoft.it.wecode.excel.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.excel.service.ExcelServiceContext;
import com.chinasoft.it.wecode.excel.service.ExcelServiceContext.Type;
import com.chinasoft.it.wecode.excel.service.IExcelDataConsumer;
import com.chinasoft.it.wecode.excel.service.IExcelImportService;

@Service
public class ExcelImportService implements IExcelImportService {

	private static final Logger logger = LogUtils.getLogger();

	@Override
	public void imports(String serviceKey, InputStream datafile) throws Exception {
		// 通过key获取配置信息
		if (!ExcelServiceContext.constainsKey(Type.importRule, serviceKey)) {
			String rulepath = "/config/excel/" + serviceKey + ".import.xml";
			logger.info("load import rule for file " + rulepath);
			InputStream ruleStream = getClass().getResourceAsStream(rulepath);
			ExcelServiceContext.put(Type.importRule, serviceKey, ruleStream);
		}

		InputStream ruleStream = ExcelServiceContext.get(Type.importRule, serviceKey);
		XLSReader reader = ReaderBuilder.buildFromXML(ruleStream);
		IExcelDataConsumer consumer = ApplicationUtils.getBean(IExcelDataConsumer.class, "excel." + serviceKey);
		Map<String, Object> beans = consumer.beansMap();
		reader.read(datafile, beans);
		logger.info("excel reader completed for context {}", beans);
		consumer.consume(beans);
		ruleStream.close(); 
	}

}
