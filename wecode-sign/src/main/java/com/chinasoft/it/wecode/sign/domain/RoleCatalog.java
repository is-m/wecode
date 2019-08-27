package com.chinasoft.it.wecode.sign.domain;

import com.chinasoft.it.wecode.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "s_role_catalog")
public class RoleCatalog extends BaseEntity {

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String catalogId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
}
