package com.chinasoft.it.wecode.excel.api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.excel.service.IExcelService;
import com.chinasoft.it.wecode.excel.utils.ExcelServletUtils;

@RestController
@RequestMapping("/services/excel")
public class ExcelCommonApi {

	@Autowired
	private IExcelService service;

	/**
	 * 下载Excel数据模版
	 * 
	 * @param resp
	 *            响应
	 * @param key
	 *            Excel ServiceKey
	 * @param name
	 *            导出模版前缀名
	 * @throws IOException
	 */
	@GetMapping("/template")
	public void downloadTemplate(HttpServletResponse resp,
			@RequestParam(value = "key", required = true) String serviceKey,
			@RequestParam(value = "name", defaultValue = "", required = false) String templateName) throws IOException {
		String fileName = StringUtils.isEmpty(templateName) ? serviceKey.replaceAll("\\.", "") : templateName;
		ExcelServletUtils.setExcelResponse(resp, fileName + "Template");
		service.downloadTemplate(serviceKey, resp.getOutputStream());
	}

}
