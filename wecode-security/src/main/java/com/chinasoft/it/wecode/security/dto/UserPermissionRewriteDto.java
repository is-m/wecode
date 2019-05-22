package com.chinasoft.it.wecode.security.dto;

import com.chinasoft.it.wecode.common.dto.BaseDto;

/**
 * 用户权限重写Dto
 */
public class UserPermissionRewriteDto extends BaseDto {

    private String roleId;

    private String dataRangeId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDataRangeId() {
        return dataRangeId;
    }

    public void setDataRangeId(String dataRangeId) {
        this.dataRangeId = dataRangeId;
    }
}
