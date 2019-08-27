package com.chinasoft.it.wecode.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 角色权限
 *
 * @author Administrator
 */
@Entity
@Table(name = "sys_role_permission")
public class RolePermission extends BaseEntity {

    /**
     * 角色ID
     */
    @Column(name="role_id")
    private String roleId;

    /**
     * 拥有的权限ID
     */
    @Column(name="permission_id")
    private String permissionId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

}
