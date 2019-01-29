package com.chinasoft.it.wecode.common.lock.provider;

import java.util.OptionalLong;
import java.util.concurrent.locks.LockSupport;

/**
 * 多线程锁提供程序
 * @author Administrator
 *
 */
public class JdkLockProvider implements LockProvider {

  @Override
  public OptionalLong lock(String key, long timeoutInMilliseconds) {
    LockSupport.parkNanos(key, System.currentTimeMillis() + LockProvider.DEFAULT_TIMEOUT_IN_MILLISECONDS);
    return null;
  }

  @Override
  public boolean postpone(String key, OptionalLong lockValue, long timeoutInMilliseconds) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean unlock(String key, OptionalLong lockValue) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public OptionalLong getActualLockValue(String key) {
    // TODO Auto-generated method stub
    return null;
  }

}
