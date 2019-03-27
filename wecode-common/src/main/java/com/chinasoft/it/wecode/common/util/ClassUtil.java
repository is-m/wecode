package com.chinasoft.it.wecode.common.util;

import org.springframework.util.ClassUtils;

public class ClassUtil {

  public static Class<?> forName(String name) {
    try {
      return ClassUtils.forName(name, null);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }
}
