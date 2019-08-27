package com.chinasoft.it.wecode.common.service.impl;

import com.chinasoft.it.wecode.security.UserPrincipal;

import java.io.Serializable;

/**
 * 环境内容提供程序，例如用户安全相关的信息
 */
public interface EnvironmentProvider {

    /**
     * 环境 Key
     *
     * @return
     */
    String key();

    /**
     * 环境值
     *
     * @param user
     * @return
     */
    Object value(UserPrincipal user);
}
