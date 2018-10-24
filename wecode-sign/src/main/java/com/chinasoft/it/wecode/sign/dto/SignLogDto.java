package com.chinasoft.it.wecode.sign.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SignLogDto {

  @NotBlank
  private String userId;

  @NotNull
  private Date signTime;
  
  @NotBlank
  private String signPoint;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getSignTime() {
    return signTime;
  }

  public void setSignTime(Date signTime) {
    this.signTime = signTime;
  }

  public String getSignPoint() {
    return signPoint;
  }

  public void setSignPoint(String signPoint) {
    this.signPoint = signPoint;
  }

  public SignLogDto() {}

  public SignLogDto(String userId, Date signTime, String signPoint) {
    this.userId = userId;
    this.signTime = signTime;
    this.signPoint = signPoint;
  }
}
