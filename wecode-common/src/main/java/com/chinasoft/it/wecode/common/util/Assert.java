package com.chinasoft.it.wecode.common.util;

import org.slf4j.Logger;

import com.google.common.base.Objects;

/**
 * 断言类
 * @author Administrator
 *
 */
public class Assert {

  private static final Logger log = LogUtils.getLogger();

  private static RuntimeException newException(Class<? extends RuntimeException> clz, String message, Object... args) {
    try {
      return clz.getConstructor(new Class<?>[] {String.class}).newInstance(StringUtil.format(message, args));
    } catch (Exception e) {
      log.warn("newException faild cause:{}", e.getMessage());
      return new IllegalArgumentException(StringUtil.format(message, args));
    }
  }

  /**
   * 为真
   */
  public static void isTrue(boolean expr, Class<? extends RuntimeException> clz, String message, Object... args) {
    if (!expr)
      throw newException(clz, message, args);
  }

  /**
   * 为真
   */
  public static void isTrue(boolean expr, String message, Object... args) throws IllegalArgumentException {
    if (!expr)
      throw new IllegalArgumentException(StringUtil.format(message, args));
  }

  /**
   * 为假
   */
  public static void isFalse(boolean expr, Class<? extends RuntimeException> clz, String message, Object... args) {
    if (expr)
      throw newException(clz, message, args);
  }

  /**
   * 为假
   */
  public static void isFalse(boolean expr, String message, Object... args) {
    if (expr)
      throw new IllegalArgumentException(StringUtil.format(message, args));
  }



  /**
   * 是否存在文本，不存在文本是抛出异常 
   */
  public static void hasText(String val, String message, Class<? extends RuntimeException> clz, Object... args) {
    if (val == null || val.trim().length() == 0)
      throw newException(clz, message, args);
  }

  /**
   * 是否存在文本，不存在文本是抛出异常 
   */
  public static void hasText(String val, String message, Object... args) {
    if (val == null || val.trim().length() == 0)
      throw new IllegalArgumentException(StringUtil.format(message, args));
  }

  /**
   * 是否相等
   */
  public static void isEquals(Object a, Object b, Class<? extends RuntimeException> clz, String message, Object... args) {
    if (!Objects.equal(a, b))
      throw newException(clz, message, args);
  }

  /**
   * 是否相等 
   */
  public static void isEquals(Object a, Object b, String message, Object... args) {
    if (!Objects.equal(a, b))
      throw new IllegalArgumentException(StringUtil.format(message, args));
  }

  /**
   * 不相等 
   */
  public static void notEquals(Object a, Object b, Class<? extends RuntimeException> clz, String message, Object... args) {
    if (Objects.equal(a, b))
      throw newException(clz, message, args);
  }

  /**
   * 不相等 
   */
  public static void notEquals(Object a, Object b, String message, Object... args) {
    if (Objects.equal(a, b))
      throw new IllegalArgumentException(StringUtil.format(message, args));
  }

  /**
   * 为空 
   */
  public static void isNull(Object val, Class<? extends RuntimeException> clz, String message, Object... args) {
    if (val != null)
      throw newException(clz, message, args);
  }

  /**
   * 为空 
   */
  public static void isNull(Object val, String message, Object... args) {
    if (val != null)
      throw new IllegalArgumentException(StringUtil.format(message, args));
  }

  /**
   * 不为空 
   */
  public static void notNull(Object val, Class<? extends RuntimeException> clz, String message, Object... args) {
    if (val == null)
      throw newException(clz, message, args);
  }

  /**
   * 不为空 
   */
  public static void notNull(Object val, String message, Object... args) {
    if (val == null)
      throw new IllegalArgumentException(StringUtil.format(message, args));
  }



}
