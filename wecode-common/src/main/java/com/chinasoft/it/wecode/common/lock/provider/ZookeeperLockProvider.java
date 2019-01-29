package com.chinasoft.it.wecode.common.lock.provider;

import java.util.OptionalLong;

public class ZookeeperLockProvider implements LockProvider {

  @Override
  public OptionalLong lock(String key, long timeoutInmilliseconds) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean postpone(String key, OptionalLong lockValue, long timeoutInmilliseconds) {
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
