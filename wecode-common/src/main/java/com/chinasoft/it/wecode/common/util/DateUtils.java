package com.chinasoft.it.wecode.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	private static SimpleDateFormat sdfyyyyMMddhhmmss = new SimpleDateFormat("yyyyMMddhhmmss");

	/**
	 * 获取当前时间（yyyyMMddhhmmss）
	 * 
	 * @return
	 */
	public static final String getCurrentYYYYMMddhhmmss() {
		return sdfyyyyMMddhhmmss.format(new Date());
	}

	private static Calendar getCalendar(int h, int m, int s, int ms) {
		return setCalendar(Calendar.getInstance(), h, m, s, ms);
	}

	private static Calendar setCalendar(Calendar calendar, int h, int m, int s, int ms) {
		calendar.set(Calendar.HOUR_OF_DAY, h);
		calendar.set(Calendar.MINUTE, m);
		calendar.set(Calendar.SECOND, s);
		calendar.set(Calendar.MILLISECOND, ms);
		return calendar;
	}

	public static Date getTodayStart() {
		return getCalendar(0, 0, 0, 0).getTime();
	}

	public static Date getTodayEnd() {
		return getCalendar(23, 59, 59, 999).getTime();
	}

	public static Date getDayStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return setCalendar(calendar, 0, 0, 0, 0).getTime();
	}

	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return setCalendar(calendar, 23, 59, 59, 999).getTime();
	}
}
