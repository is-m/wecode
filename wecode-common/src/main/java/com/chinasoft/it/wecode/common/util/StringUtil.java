package com.chinasoft.it.wecode.common.util;

public class StringUtil {

	public static String getString(String value, String defaultValue) {
		return isEmpty(value) ? defaultValue : value;
	}

	public static boolean isEmpty(String val) {
		return val == null || val.trim().length() == 0;
	}
}
