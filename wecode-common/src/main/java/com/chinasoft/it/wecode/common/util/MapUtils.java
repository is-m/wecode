package com.chinasoft.it.wecode.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapUtils {

    private static final Map<Object, Object> EMPTY = Collections.unmodifiableMap(new HashMap<>(0));

    /**
     * 获取空MAP
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> unmodifyEmptyMap() {
        return (Map<K, V>) EMPTY;
    }

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
