package com.chinasoft.it.wecode.common.util;

public class StringUtil {

  private static final char[] BCHARS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
      'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
      'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', '!', '"', '#', '$', '%', '&', '\'', '(', ')', ',', '-', '.', ':', ';', '<',
      '>', '@', '[', ']', '^', '`', '_', '{', '|', '}', '~', '?'};

  public static String getString(String value, String defaultValue) {
    return isEmpty(value) ? defaultValue : value;
  }

  public static boolean isEmpty(String val) {
    return val == null || val.trim().length() == 0;
  }

  /**
   * 随机生成字符串 （可以用于加密KEY）
   * 
   * @param length
   *            生成长度
   * @return
   */
  public static String random(int length) {
    if (length < 1) {
      throw new IllegalArgumentException("length cannot be less then 1 ");
    }

    int codeLength = BCHARS.length;
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < length; i++) {
      result.append(BCHARS[(int) (Math.random() * codeLength)]);
    }

    return result.toString();
  }

  public static boolean emptyOrIgnoreCaseEquals(String val, String... equalsValues) {
    if (isEmpty(val)) {
      return true;
    }
    if (equalsValues != null && equalsValues.length > 0) {
      for (String item : equalsValues) {
        if (val.equalsIgnoreCase(item)) {
          return true;
        }
      }
    }
    return false;
  }
}
