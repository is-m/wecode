package com.chinasoft.it.wecode.security.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.exception.AuthenticationException;
import com.chinasoft.it.wecode.security.Role;
import com.chinasoft.it.wecode.security.UserPrincipal;
import com.chinasoft.it.wecode.security.config.SecurityProperties;

public class UserContextManager {

  private static ThreadLocal<UserPrincipal> $ = new ThreadLocal<>();

  private static final List<Role> GUEST_ROLES = Arrays.asList(new Role() {
    @Override
    public String getCode() {
      return "GUEST";
    }

    @Override
    public Set<String> getPermissionCodeSet() {
      return null;
    }
  });

  public static void set(UserPrincipal user) {
    $.set(user);
  }

  public static void set(String uid) {
    // 加载用户信息
    // UserProvider bean = ApplicationUtils.getBean(UserProvider.class);
    // $.set(bean.get(uid));
    $.set(new UserPrincipal() {

      @Override
      public String getUid() {
        return uid;
      }

      @Override
      public List<Role> getRoles() {
        return GUEST_ROLES;
      }

      @Override
      public Role getActivedRole() {
        return GUEST_ROLES.get(0);
      }
    });
  }

  public static UserPrincipal get() {
    return $.get();
  }

  public static UserPrincipal get(boolean requried) throws AuthenticationException {
    return Optional.ofNullable(get()).orElseThrow(AuthenticationException::new);
  }

  /**
   * 是否为超级管理员
   * @return
   */
  public static boolean isSuperAdmin() throws AuthenticationException {
    String adminCode = ApplicationUtils.getBean(SecurityProperties.class).getAdminRoleCode();
    return get(true).getRoles().parallelStream().anyMatch(role -> role != null && adminCode.equals(role.getCode()));
  }

  public static void remove() {
    if ($.get() != null) {
      $.remove();
    }
  }
}
