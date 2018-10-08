package com.chinasoft.it.wecode.common.util;

public class ArrayUtil {

	public static boolean contains(Object[] array, Object obj) {
		if (!isEmpty(array) && obj != null) {
			for (Object item : array) {
				if (item != null && obj.equals(item)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	public static boolean isArray(Object obj) {
		return obj != null && obj.getClass().isArray();
	}

}
