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
   * 当前活动的角色，不会返回空，只要是登陆用户，哪怕没有角色时也会返回访客角色
   * 
   * @return
   */
  Role getCurrentRole();

  /**
   * 当前用户角色列表，不会返回空，只要是登陆用户，哪怕没有角色时也会返回访客角色
   * 
   * @return
   */
  List<Role> getRoles();
  
  /**
   * 当前用户语言
   * @return
   */
  String getLanguage();

}
