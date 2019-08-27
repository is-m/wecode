package com.chinasoft.it.wecode.security.service.impl;

import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.exception.AuthenticationException;
import com.chinasoft.it.wecode.security.Role;
import com.chinasoft.it.wecode.security.UserPrincipal;
import com.chinasoft.it.wecode.security.config.SecurityProperties;
import com.chinasoft.it.wecode.security.spi.UserDetailService;

import java.util.*;

public class UserContextManager {

    private static ThreadLocal<UserPrincipal> $ = new ThreadLocal<>();

    public static void set(UserPrincipal user) {
        $.set(user);
    }

    public static void set(String uid) {
        UserDetailService userDetailService = ApplicationUtils.getBean(UserDetailService.class);
        UserPrincipal user = userDetailService.findByUid(uid);
        $.set(user);
    }

    public static UserPrincipal get() {
        return $.get();
    }

    public static UserPrincipal get(boolean requried) throws AuthenticationException {
        return Optional.ofNullable(get()).orElseThrow(AuthenticationException::new);
    }

    /**
     * 是否为超级管理员
     *
     * @return
     */
    public static boolean isSuperAdmin() throws AuthenticationException {
        UserPrincipal user = get();
        if (user == null) return false;
        String adminCode = ApplicationUtils.getBean(SecurityProperties.class).getAdminRoleCode();
        return Objects.equals(adminCode, user.getCurrentRole().getCode());
    }

    public static void remove() {
        if ($.get() != null) {
            $.remove();
        }
    }
}
