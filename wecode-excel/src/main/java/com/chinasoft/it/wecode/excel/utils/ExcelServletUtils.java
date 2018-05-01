package com.chinasoft.it.wecode.excel.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.chinasoft.it.wecode.common.util.DateUtils;
import com.chinasoft.it.wecode.common.util.LogUtils;

public class ExcelServletUtils {

	private static final Logger logger = LogUtils.getLogger();

	private static final String MIME = "application/octet-stream";

	/**
	 * TODO 文件名不应该是这里写死
	 * 
	 * @param resp
	 * @param fileName
	 */
	public static void setExcelResponse(HttpServletResponse resp, String fileName) {
		resp.setContentType(MIME);
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.warn("fileName encode faild", e.getMessage());
		}

		resp.setHeader("content-disposition",
				"attachment;filename=" + fileName + DateUtils.getCurrentYYYYMMddhhmmss() + ".xlsx");
	}
}
