package com.chinasoft.it.wecode.excel.template.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.chinasoft.it.wecode.excel.template.data.DataProvider;

public class ExcelTemplateUtils {

	private static ExecutorService es = Executors.newFixedThreadPool(2);

	public static void tempalte(String templatePath, OutputStream os, DataProvider provider)
			throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {
		ExcelTemplateFactory.getEngine().process(new FileInputStream(templatePath), os, provider);
	}
 
	public static void tempalte(InputStream is, OutputStream os, DataProvider provider)
			throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {
		ExcelTemplateFactory.getEngine().process(is, os, provider);
	}
 
	/**
	 * 异步导出
	 * 
	 * @param templatePath
	 *            模版路径
	 * @param provider
	 *            数据提供者
	 * @param callback
	 *            回调
	 * @return 异步ID
	 */
	public static String tempalte(String templatePath, DataProvider provider, Consumer<AsyncResult> callback) {
		String taskId = UUID.randomUUID().toString().replaceAll("-", "");
		es.submit(new Runnable() {

			@Override
			public void run() {
				try {
					String filePath = "D:\\excel\\tg\\" + taskId;
					ExcelTemplateFactory.getEngine().process(new FileInputStream(templatePath),
							new FileOutputStream(filePath), provider);
					callback.accept(AsyncResult.ok(taskId, filePath));
				} catch (Exception e) {
					callback.accept(AsyncResult.error(taskId, e.getMessage()));
				}
			}
		});

		return taskId;

	}
}
