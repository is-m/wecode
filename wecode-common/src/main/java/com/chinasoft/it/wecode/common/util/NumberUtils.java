package com.chinasoft.it.wecode.common.util;

import org.apache.commons.lang3.StringUtils;

public class NumberUtils {

  /**
   * 解析数值
   * 
   * @param numberString
   * @param defaultValue
   * @return
   */
  public static int tryParse(String numberString, int defaultValue) {
    if (StringUtils.isEmpty(numberString) || !StringUtils.isNumeric(numberString)) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(numberString);
    } catch (Throwable e) {
      return defaultValue;
    }
  }
}
