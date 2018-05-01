package com.chinasoft.it.wecode.excel.service;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.util.StreamUtils;

import com.chinasoft.it.wecode.common.proxy.InvocationAfterEvent;
import com.chinasoft.it.wecode.common.proxy.InvocationFactory;
import com.chinasoft.it.wecode.common.proxy.MyInvocationHandler;
import com.chinasoft.it.wecode.common.proxy.MyInvocationHandler.MethodContext;
import com.chinasoft.it.wecode.common.util.LogUtils;

public class ExcelServiceContext {

	private static Logger logger = LogUtils.getLogger();

	private static Map<String, Object> context = new ConcurrentHashMap<>(new HashMap<>());

	// about queue
	// https://blog.csdn.net/tingting256/article/details/52488651
	private static List<TimeoutClosedStream> autoCloseStreams = new Vector<>();
	private static final int period = 1000 * 20; // 20 s

	// 自动关闭流的定时任务
	private static final Timer autoCloseStreamsTimer;

	static {
		autoCloseStreamsTimer = new Timer();
		autoCloseStreamsTimer.schedule(new TimerTask() {
			public void run() {
				if (autoCloseStreams.size() > 0) {
					autoCloseStreams.removeIf(stream -> stream.close());
					logger.info("auto close stream task has count {} ", autoCloseStreams.size());
				}
			}
		}, period, period);
	}

	private static void put(String key, Object value) {
		context.put(key, value);
	}

	private static Object get(String key) {
		return context.get(key);
	}

	private static boolean containsKey(String key) {
		return context.containsKey(key);
	}

	public static void putExportTemplate(String key, InputStream input) throws IOException {
		put(Type.exportTemplate, key, input);
	}

	public static InputStream getExportTemplate(String key) {
		return get(Type.exportTemplate, key);
	}

	public static boolean containsExportTemplateKey(String key) {
		return constainsKey(Type.exportTemplate, key);
	}

	public static InputStream get(Type type, String key) {
		Object inputBytes = get(type + key);
		Objects.requireNonNull(inputBytes, "no found resource for service key " + key);
		ByteArrayInputStream tempInputStream = new ByteArrayInputStream((byte[]) inputBytes);
		autoCloseStreams.add(new TimeoutClosedStream(tempInputStream));
		return tempInputStream;
	}

	public static void put(Type type, String key, InputStream input) throws IOException {
		Objects.requireNonNull(input, "未找打 导入/导出 Excel 服务KEY= [" + key + "] 以及服务类型= [" + type
				+ "] 的系统资源,请检查资源是否在项目中，以及是否包含在打包后的jar文件中，如果未包含，则需要确认是否是maven的resource/filter配置导致排除掉了的相关资源");
		put(type + key, StreamUtils.copyToByteArray(input));
		input.close();
	}

	public static boolean constainsKey(Type type, String key) {
		return containsKey(type + key);
	}

	/**
	 * 导入导出服务类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum Type {
		/**
		 * 导出模版
		 */
		exportTemplate,
		/**
		 * 导入模版
		 */
		importTemplate,
		/**
		 * 导入解析规则
		 */
		importRule
	}

	private static class TimeoutClosedStream {

		private Logger logger = LogUtils.getLogger();
		private Closeable stream;

		private int retryCount = 0;
		private boolean closed = false;
		private long outtime = -1;
		private long overtime = 1000 * 60 * 3; // 3 min

		public TimeoutClosedStream(Closeable stream) {
			this.stream = stream;
			outtime = System.currentTimeMillis() + overtime;
		}

		public boolean close() {
			if (outtime < System.currentTimeMillis() && !closed && stream != null) {
				try {
					logger.info("stream time out auto close");
					stream.close();
					stream = null;
					closed = true;
				} catch (Exception e) {
					// TODO：如果抛出的是流已经关闭的异常则应该至直接表示流关闭了
					logger.error("timeout close stream faild", e);
					if (retryCount++ > 3) {
						stream = null;
						closed = true;
						logger.error("close stream out of max retry count , this context cleaned.");
					}
				}
			}

			return closed;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof TimeoutClosedStream))
				return false;
			TimeoutClosedStream that = (TimeoutClosedStream) obj;
			return this.stream != null && that.stream != null && this.stream.equals(that.stream);
		}

		@Override
		public int hashCode() {
			return stream == null ? 0 : stream.hashCode();
		}

	}
}
