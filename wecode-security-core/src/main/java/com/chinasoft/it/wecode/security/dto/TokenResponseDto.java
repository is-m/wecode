package com.chinasoft.it.wecode.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * TOKEN响应
 * @author Administrator
 *
 */
@ApiModel("Token响应")
public class TokenResponseDto {

  /**
   * token值
   */
  @ApiModelProperty(name="token",notes="令牌")
  private String token;

  /**
   * 用户身份
   */
  @ApiModelProperty(name="identifier",notes="用户身份ID")
  private String identifier;

  /**
   * 有效时长
   */
  @ApiModelProperty(name="expire",notes="过期时间")
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
