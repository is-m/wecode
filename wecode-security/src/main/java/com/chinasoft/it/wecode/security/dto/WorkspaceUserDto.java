package com.chinasoft.it.wecode.security.dto;

public class WorkspaceUserDto extends UserDto {

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
