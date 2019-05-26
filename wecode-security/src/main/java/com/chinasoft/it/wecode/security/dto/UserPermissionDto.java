package com.chinasoft.it.wecode.security.dto;

import java.util.List;

import com.chinasoft.it.wecode.common.dto.BaseDto;
import com.chinasoft.it.wecode.security.domain.DataRange;
import com.chinasoft.it.wecode.security.domain.Role;

/**
 * 用户授权Dto
 */
public class UserPermissionDto extends BaseDto {

    /**
     * 用户权限ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 数据范围ID
     */
    private String dataRangeId;

    /**
     * 数据范围名称
     */
    private String dataRangeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDataRangeId() {
        return dataRangeId;
    }

    public void setDataRangeId(String dataRangeId) {
        this.dataRangeId = dataRangeId;
    }

    public String getDataRangeName() {
        return dataRangeName;
    }

    public void setDataRangeName(String dataRangeName) {
        this.dataRangeName = dataRangeName;
    }

    public static UserPermissionDto of(String id,String userId, String roleId, String roleName, String dataRangeId, String dataRangeName) {
        UserPermissionDto result = new UserPermissionDto();
        result.setId(id);
        result.setUserId(userId);
        result.setRoleId(roleId);
        result.setRoleName(roleName);
        result.setDataRangeId(dataRangeId);
        result.setDataRangeName(dataRangeName);
        return result;
    }
}
