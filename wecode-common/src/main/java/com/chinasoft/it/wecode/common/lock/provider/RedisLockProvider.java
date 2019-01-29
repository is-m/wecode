package com.chinasoft.it.wecode.common.lock.provider;

import java.util.Objects;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


@SuppressWarnings({"rawtypes", "unchecked"})
public class RedisLockProvider implements LockProvider {

  private RedisTemplate redisTemplate;

  private ValueOperations valueOps;

  public RedisLockProvider(RedisTemplate redisTemplate) {
    Objects.requireNonNull(redisTemplate);
    this.redisTemplate = redisTemplate;
    this.valueOps = redisTemplate.opsForValue();
  }

  @Override
  public OptionalLong lock(String key, long timeoutInMilliseconds) {
    long timeouts = resolveTimeoutInMilliseconds(timeoutInMilliseconds);
    long lockValue = System.currentTimeMillis() + timeouts;
    // setIfAbsent is redis setNX
    if (valueOps.setIfAbsent(key, String.valueOf(lockValue))) {
      redisTemplate.expire(key, timeouts, TimeUnit.MILLISECONDS);
      return OptionalLong.of(lockValue);
    }
    return OptionalLong.empty();
  }

  @Override
  public boolean postpone(String key, OptionalLong lockValue, long timeoutInMilliseconds) {
    return lockValue.equals(getActualLockValue(key))
        ? redisTemplate.expire(key, resolveTimeoutInMilliseconds(timeoutInMilliseconds), TimeUnit.MILLISECONDS)
        : false;
  }

  @Override
  public boolean unlock(String key, OptionalLong lockValue) {
    return lockValue.equals(getActualLockValue(key)) ? redisTemplate.delete(key) : false;
  }

  @Override
  public OptionalLong getActualLockValue(String key) {
    Object redisValue = valueOps.get(key);
    return redisValue != null ? OptionalLong.of(Long.valueOf(((String) redisValue))) : OptionalLong.empty();
  }

}
