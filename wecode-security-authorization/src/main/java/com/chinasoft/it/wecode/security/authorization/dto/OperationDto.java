package com.chinasoft.it.wecode.security.authorization.dto;

import java.util.Objects;

import com.chinasoft.it.wecode.annotations.security.Operate.Policy;

/**
 * 受控资源操作
 * 
 * @author Administrator
 *
 */
public class OperationDto extends BaseAuthorizationDto {

  private static final long serialVersionUID = -7150627658674509455L;

  /**
   * 受控策略
   */
  private Policy policy;

  /**
   * 所属资源
   */
  private String resourceCode;

  public Policy getPolicy() {
    return policy;
  }

  public void setPolicy(Policy policy) {
    this.policy = policy;
  }

  public String getResourceCode() {
    return resourceCode;
  }

  public void setResourceCode(String resourceCode) {
    this.resourceCode = resourceCode;
  }

  @Override
  public String permissionCode() {
    Objects.requireNonNull(resourceCode, "resource cannot be null");
    return String.format("%s%s%s", resourceCode, SPERATOR, this.getCode());
  }



  @Override
  public String toString() {
    return "OperationDto [policy=" + policy + ", resourceCode=" + resourceCode + "]";
  }

  

}
