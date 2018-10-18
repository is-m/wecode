package com.chinasoft.it.wecode.security.dto;

/**
 * TOKEN响应
 * @author Administrator
 *
 */
public class TokenResponseDto {

  /**
   * token值
   */
  private String token;

  /**
   * 用户身份
   */
  private String identifier;

  /**
   * 有效时长
   */
  private Long expire;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getExpire() {
    return expire;
  }

  public void setExpire(Long expire) {
    this.expire = expire;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public TokenResponseDto() {}

  public TokenResponseDto(String token, Long expire) {
    this(token, null, expire);
  }

  public TokenResponseDto(String token, String identifier, Long expire) {
    this.token = token;
    this.identifier = identifier;
    this.expire = expire;
  }

}
