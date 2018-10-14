package com.chinasoft.it.wecode.security.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 数据范围明细
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_data_range_item")
public class DataRangeItem extends BaseEntity {

  /**
   * 数据范围ID
   */
  private String dataRangeId;

  /**
   * 维度ID
   */
  private String dimensionId;

  /**
   * 维度值，多个使用逗号分割,
   */
  private String dimensionValue;

  public String getDataRangeId() {
    return dataRangeId;
  }

  public void setDataRangeId(String dataRangeId) {
    this.dataRangeId = dataRangeId;
  }

  public String getDimensionId() {
    return dimensionId;
  }

  public void setDimensionId(String dimensionId) {
    this.dimensionId = dimensionId;
  }

  public String getDimensionValue() {
    return dimensionValue;
  }

  public void setDimensionValue(String dimensionValue) {
    this.dimensionValue = dimensionValue;
  }

}
