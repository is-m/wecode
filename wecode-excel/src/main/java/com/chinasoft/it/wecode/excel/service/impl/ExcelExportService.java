package com.chinasoft.it.wecode.excel.service.impl;

import java.io.OutputStream;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinasoft.it.wecode.excel.service.ExcelDataConfig;
import com.chinasoft.it.wecode.excel.service.IExcelDataProvider;
import com.chinasoft.it.wecode.excel.service.IExcelExportService;

@Named
public class ExcelExportService implements IExcelExportService {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelExportService.class);

	@Override
	public void export(ExcelDataConfig config, IExcelDataProvider provider, OutputStream output) throws Exception {
		logger.info("export data of config {}",config);
		if (config.isPageable()) {
			while (config.nextPage()) {
				logger.info("export data of page {}",config);
				Object prod = provider.produce(config);
				logger.info("export page data {}",prod);
			}
		} else { 
			logger.info("export data of once");
			Object prod = provider.produce(config);
			logger.info("export data {}",prod);
		}
	}

}
