package com.chinasoft.it.wecode.common.lock.provider;

import java.util.OptionalLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 锁提供接口
 * @author Administrator
 *
 */
public interface LockProvider {

  /**
   * 默认日志记录
   */
  Logger LOGGER = LoggerFactory.getLogger(LockProvider.class);

  /**
   * 默认超时时间
   * FIXME:考虑跟LOCK注解中的同一个常量内容是否需要同源
   */
  long DEFAULT_TIMEOUT_IN_MILLISECONDS = 1000 * 60 * 2;

  /**
   * 加锁
   * @param key
   * @return
   */
  OptionalLong lock(String key, long timeoutInMilliseconds);

  /**
   * 锁延迟
   * @param key
   * @param lockValue
   * @return
   */
  boolean postpone(String key, OptionalLong lockValue, long timeoutInMilliseconds);

  /**
   * 锁延迟（不安全操作）
   * @param key
   * @return
   */
  @Deprecated
  default boolean postpone(String key, long timeoutInMilliseconds) {
    return postpone(key, getActualLockValue(key), timeoutInMilliseconds);
  }

  /**
   * 表决最终超时时间
   * @param timeoutInMilliseconds
   * @return
   */
  default long resolveTimeoutInMilliseconds(long timeoutInMilliseconds) {
    return timeoutInMilliseconds > 0 ? timeoutInMilliseconds : DEFAULT_TIMEOUT_IN_MILLISECONDS;
  }

  /**
   * 解锁
   * @param key
   * @param lockValue
   * @return
   */
  boolean unlock(String key, OptionalLong lockValue);

  /**
   * 解锁(不安全操作)
   * @param key
   * @return
   */
  @Deprecated
  default boolean unlock(String key) {
    return unlock(key, getActualLockValue(key));
  }

  /**
   * 获取真实的锁值,建议仅限于 safeOp 方法中使用，当然调试时也可以考虑使用
   * @param key
   * @return
   */
  OptionalLong getActualLockValue(String key);


  default Logger getLogger() {
    return LOGGER;
  }

  /**
   * 安全操作
   * @param key
   * @return
   */
  default boolean safeOp(String key, OptionalLong lockValue, Runnable runnable) {
    Logger logger = getLogger();
    if (StringUtils.isEmpty(key)) {
      logger.warn("lock key is null or empty");
    } else if (lockValue == null || !lockValue.isPresent()) {
      logger.warn("lockValue is null");
    } else if (runnable == null) {
      logger.warn("runnable is null");
    } else {
      OptionalLong actualLockValue = getActualLockValue(key);
      if (actualLockValue != null && actualLockValue.equals(lockValue)) {
        runnable.run();
        return true;
      }

    }
    return false;
  }
}
