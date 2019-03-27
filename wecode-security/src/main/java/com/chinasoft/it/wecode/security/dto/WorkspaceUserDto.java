package com.chinasoft.it.wecode.security.dto;

public class WorkspaceUserDto extends UserDto {

  private static final long serialVersionUID = -5375863340101955059L;
  
  /**
   * 当前激活的角色
   */
  private String activeRole;

  public String getActiveRole() {
    return activeRole;
  }

  public void setActiveRole(String activeRole) {
    this.activeRole = activeRole;
  }
}
