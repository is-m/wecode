package com.chinasoft.it.wecode.sign.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chinasoft.it.wecode.base.BaseEntity;

@Entity
@Table(name = "sn_sign")
public class Sign extends BaseEntity {


  /**
   * sign user
   */
  private String userId;

  /**
   * sign date
   */
  @Temporal(TemporalType.DATE)
  private Date signDate;

  /**
   * begin time
   */
  private String beginTime;

  /**
   * begin point
   */
  private String beginPoint;

  /**
   * end time
   */
  private String endTime;

  /**
   * end point
   */
  private String endPoint;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getBeginPoint() {
    return beginPoint;
  }

  public void setBeginPoint(String beginPoint) {
    this.beginPoint = beginPoint;
  }

  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public Date getSignDate() {
    return signDate;
  }

  public void setSignDate(Date signDate) {
    this.signDate = signDate;
  }

  public String getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

}
