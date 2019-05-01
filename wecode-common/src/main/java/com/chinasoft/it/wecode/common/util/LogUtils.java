package com.chinasoft.it.wecode.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogUtils {

  private LogUtils() {}

  /**
   * 获取日志
   * @return
   */
  public static Logger getLogger() {
    try {
      StackTraceElement[] stackTrace = (new Throwable()).getStackTrace();
      String className = stackTrace[1].getClassName();
      return LoggerFactory.getLogger(Class.forName(className));
    } catch (Exception e) {
      throw new RuntimeException("获取日志对象异常", e);
    }
  }

  /**
   * 获取日志
   * @param clz
   * @return
   */
  public static Logger getLogger(Class<?> clz) {
    return LoggerFactory.getLogger(clz);
  }

}
