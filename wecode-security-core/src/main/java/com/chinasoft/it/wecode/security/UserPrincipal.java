package com.chinasoft.it.wecode.security;

import java.util.List;

public interface UserPrincipal {

    /**
     * 获取用户标识（用户名，id，手机，邮箱）
     *
     * @return
     */
    String getUid();

    /**
     * 当前活动的角色
     *
     * @return
     */
    Role getCurrentRole();

    /**
     * 当前用户角色列表
     *
     * @return
     */
    List<Role> getRoles();

}
