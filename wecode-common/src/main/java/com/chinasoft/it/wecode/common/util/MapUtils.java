package com.chinasoft.it.wecode.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapUtils {

	public static Map<String, Object> newMap(Object... keyValues) {
		Map<String, Object> map = new HashMap<>();

		if (keyValues != null && keyValues.length > 0) {
			for (int i = 0; i < keyValues.length; i += 2) {
				Object key = keyValues[i];
				Objects.requireNonNull(key, "key 不能为空");
				map.put(key.toString(), keyValues[i + 1]);
			}
		}

		return map;
	}
}
