package com.chinasoft.it.wecode.common.constant;

/**
 * 缓存常量
 */
public class CacheConstants {

  // 缓存名取值为application.properties里面的spring.cache.cache-names属性
  /**
   * 缓存适用：全局系统缓存
   */
  public static final String CACHE_SYS = "SYSTEM";

  /**
   * 缓存适用：系统用户及相关
   */
  public static final String CACHE_SYS_USER = "SYSTEM_USER";

  /**
   * 缓存适用：系统配置，例如数据字典，国际化
   */
  public static final String CACHE_SYS_CONFIG = "SYSTEM_CONFIG";

  /**
   * 缓存适用：系统支持类，例如
   */
  public static final String CACHE_SYS_SUPPORT = "SYSTEM_SUPPORT";

  /**
   *
   */
  public static final String CACHE_KEY_USER_ENV_PREFIX = "$SYS_ENV_USER_";

}
