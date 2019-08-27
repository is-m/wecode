package com.chinasoft.it.wecode.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 集合工具
 * @author Administrator
 *
 */
public class MapUtils {

  public static Map<String, Object> newMap(Object... keyValues) {
    if (keyValues != null && keyValues.length > 0) {
      int len = keyValues.length;
      Map<String, Object> map = new HashMap<>(len / 2 + 1);
      for (int i = 0; i < len; i += 2) {
        map.put(Objects.requireNonNull(keyValues[i], "key 不能为空").toString(), keyValues[i + 1]);
      }
      return map;
    }
    return new HashMap<>(0);
  }
}
