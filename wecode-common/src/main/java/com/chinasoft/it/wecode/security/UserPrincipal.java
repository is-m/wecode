package com.chinasoft.it.wecode.security;

import java.io.Serializable;
import java.util.List;


public interface UserPrincipal {

  /**
   * 获取用户标识（用户名，id，手机，邮箱）
   * 
   * @return
   */
  Serializable getUid();

  /**
   * 当前活动的角色
   * 
   * @return
   */
  Role getActivedRole();

  /**
   * 当前用户角色列表
   * 
   * @return
   */
  List<Role> getRoles();

}
