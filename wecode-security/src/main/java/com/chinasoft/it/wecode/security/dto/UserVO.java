package com.chinasoft.it.wecode.security.dto;

import com.chinasoft.it.wecode.security.Role;
import com.chinasoft.it.wecode.security.UserPrincipal;

import java.io.Serializable;
import java.util.List;

public class UserVO implements UserPrincipal {

    private String uid;

    private String username;

    private Role currentRole;

    private List<Role> roles;

    private String language;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCurrentRole(Role currentRole) {
        this.currentRole = currentRole;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public Serializable getUid() {
        return uid;
    }

    @Override
    public Role getCurrentRole() {
        return currentRole;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public String getLanguage() {
        return language;
    }
}
