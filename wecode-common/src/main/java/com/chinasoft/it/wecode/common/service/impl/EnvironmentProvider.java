package com.chinasoft.it.wecode.common.service.impl;

import java.io.Serializable;

/**
 * 环境内容提供程序，例如用户安全相关的信息
 */
public interface EnvironmentProvider {

    /**
     * 环境Key
     * @return
     */
    String key();

    /**
     * 环境值
     * @param uid
     * @return
     */
    Serializable value(String uid);
}
