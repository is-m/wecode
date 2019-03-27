package com.chinasoft.it.wecode.common.validation.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/**
 * 字段校验
 * @author Administrator
 *
 */
public class FieldValidationBean implements Serializable {
  private static final long serialVersionUID = -6273052492195599431L;

  /**
   * 等待校验的字段
   */
  private String field;

  /**
   * 校验器
   */
  private List<ValidationBean> validations = new ArrayList<>();

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public List<ValidationBean> getValidations() {
    return validations;
  }

  public FieldValidationBean validation(String type, Map<String, Object> args, String msg) {
    return this.validation(type, args, msg, 9);
  }

  public FieldValidationBean validation(String type, Map<String, Object> args, String msg, int priority) {
    if (StringUtils.isNotBlank(type)) {
      this.validations.add(ValidationBean.of(type, args, msg, priority));
    }
    return this;
  }

  public boolean hasValidation() {
    return this.validations != null && !this.validations.isEmpty();
  }

  public FieldValidationBean() {}

  public FieldValidationBean(String field) {
    this.field = field;
  }

  public FieldValidationBean(String field, String type, Map<String, Object> args, String msg) {
    this.field = field;
    this.validation(type, args, msg);
  }

  public static FieldValidationBean of(String field, String type, Map<String, Object> args, String msg) {
    return new FieldValidationBean(field, type, args, msg);
  }

  public static FieldValidationBean of(String field) {
    return new FieldValidationBean(field);
  }

}
