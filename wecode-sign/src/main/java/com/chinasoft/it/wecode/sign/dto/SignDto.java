package com.chinasoft.it.wecode.sign.dto;

import javax.validation.constraints.NotBlank;

public class SignDto {

  /**
   * sign user
   */
  @NotBlank
  private String userId;

  /**
   * sign point
   */
  @NotBlank
  private String point;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPoint() {
    return point;
  }

  public void setPoint(String point) {
    this.point = point;
  }
}
