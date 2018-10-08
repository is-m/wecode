package com.chinasoft.it.wecode.common.util;

import org.springframework.util.StringUtils;

public class NumberUtils {

  public static int tryParse(String numberString, int defaultValue) {
    if (StringUtils.isEmpty(numberString))
      return defaultValue;

    try {
      return Integer.parseInt(numberString);
    } catch (Throwable e) {
      return defaultValue;
    }
  }
}
