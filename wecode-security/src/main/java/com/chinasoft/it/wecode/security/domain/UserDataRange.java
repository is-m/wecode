package com.chinasoft.it.wecode.security.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 用户数据权限
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_user_data_range")
public class UserDataRange extends BaseEntity {

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 数据范围ID
   */
  private String dataRangeId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getDataRangeId() {
    return dataRangeId;
  }

  public void setDataRangeId(String dataRangeId) {
    this.dataRangeId = dataRangeId;
  }

}
