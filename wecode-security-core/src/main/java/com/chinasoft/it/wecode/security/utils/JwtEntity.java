package com.chinasoft.it.wecode.security.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Json web token entity
 * 
 * @author Administrator
 *
 */
public class JwtEntity {

  /**
   * 用户标识（帐号ID ， 用户名， 电话， 邮箱 ?）
   */
  private String uid;

  /**
   * 创建时间
   */
  private long iat;

  /**
   * 过期时间
   */
  private long exp;

  /**
   * 负荷
   */
  private Map<String, Object> payload;

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public long getIat() {
    return iat;
  }

  public void setIat(long iat) {
    this.iat = iat;
  }

  public long getExp() {
    return exp;
  }

  public void setExp(long exp) {
    this.exp = exp;
  }

  public Map<String, Object> getPayload() {
    return payload;
  }

  public void setPayload(Map<String, Object> payload) {
    this.payload = payload;
  }

  /**
   * 获取从开始到结束的有效时长
   * @return
   */
  public long effectiveTimes() {
    return exp - iat;
  }

  public JwtEntity() {}

  public JwtEntity(String uid, long iat, long exp) {
    this.uid = uid;
    this.iat = iat;
    this.exp = exp;
    this.payload = new HashMap<>(0);
  }

  public JwtEntity(Map<String, Object> map) throws IllegalArgumentException {
    if (map == null || map.isEmpty())
      throw new IllegalArgumentException("payload is null or empty");

    this.uid = (String) map.remove("sub");
    this.iat = Long.valueOf(map.remove("iat").toString());
    this.exp = Long.valueOf(map.remove("exp").toString());
    this.payload = map;
  }
}
