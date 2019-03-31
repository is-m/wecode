package com.chinasoft.it.wecode.common.validation.service.impl;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 验证描述对象
 * @author Administrator
 *
 */
@JsonIgnoreProperties({"priority"})
public class ValidationBean implements Serializable {

  private static final long serialVersionUID = -7456556283575808176L;

  /**
   * 校验类型
   */
  private String type;

  /**
   * 校验参数，例如如果限制属性最大长度时，该对象可能存在一个 max = xx 的参数
   */
  private Map<String, Object> arg;

  /**
   * 错误消息
   */
  private String msg;

  /**
   * 优先级,值越小，校验的顺序越早
   */
  private int priority = 9;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Map<String, Object> getArg() {
    return arg;
  }

  public void setArg(Map<String, Object> args) {
    this.arg = args;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public ValidationBean() {}

  public ValidationBean(String type, Map<String, Object> arg, String msg, int priority) {
    this.type = type;
    this.arg = arg;
    this.msg = msg;
    this.priority = priority;
  }

  public static ValidationBean of(String type, Map<String, Object> arg, String msg) {
    return new ValidationBean(type, arg, msg, 9);
  }

  public static ValidationBean of(String type, String msg) {
    return new ValidationBean(type, null, msg, 9);
  }

  public static ValidationBean of(String type, Map<String, Object> arg, String msg, int priority) {
    return new ValidationBean(type, arg, msg, priority);
  }
}
