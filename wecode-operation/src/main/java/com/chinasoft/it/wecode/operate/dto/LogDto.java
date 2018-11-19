package com.chinasoft.it.wecode.operate.dto;

/**
 * 数据对象
 * @author Administrator
 *
 */
public class LogDto {

  /**
   * metric 指标
   */
  private String m;
  
  /**
   * object Id 触发对象
   * 需要是绝对的值
   */
  private String o;
  
  /**
   * timestamp 触发时间
   */
  private String t;
  
  /**
   * value 值
   */
  private String v;
  
  /**
   * 说明，补充信息
   */
  private String r;

  public String getM() {
    return m;
  }

  public void setM(String m) {
    this.m = m;
  }

  public String getO() {
    return o;
  }

  public void setO(String o) {
    this.o = o;
  }

  public String getT() {
    return t;
  }

  public void setT(String t) {
    this.t = t;
  }

  public String getV() {
    return v;
  }

  public void setV(String v) {
    this.v = v;
  }

  public String getR() {
    return r;
  }

  public void setR(String r) {
    this.r = r;
  }

  public LogDto() {
  }

  public LogDto(String m, String o, String t, String v, String r) {
    this.m = m;
    this.o = o;
    this.t = t;
    this.v = v;
    this.r = r;
  }
  
}
