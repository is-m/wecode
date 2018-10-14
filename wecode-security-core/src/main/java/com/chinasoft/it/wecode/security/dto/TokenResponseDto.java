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

  public TokenResponseDto() {}

  public TokenResponseDto(String token, Long expire) {
    this.token = token;
    this.expire = expire;
  }

}
