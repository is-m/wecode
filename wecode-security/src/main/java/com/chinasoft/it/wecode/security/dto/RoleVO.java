package com.chinasoft.it.wecode.security.dto;

import com.chinasoft.it.wecode.security.Role;

import java.io.Serializable;
import java.util.Set;

public class RoleVO implements Role, Serializable {

    private String id;

    private String code;

    private String name;

    private Set<String> permissionCodeSet;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Set<String> getPermissionCodeSet() {
        return permissionCodeSet;
    }

    @Override
    public boolean isSysAdmin() {
        return false;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermissionCodeSet(Set<String> permissionCodeSet) {
        this.permissionCodeSet = permissionCodeSet;
    }
}
