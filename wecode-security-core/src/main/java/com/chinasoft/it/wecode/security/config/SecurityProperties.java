package com.chinasoft.it.wecode.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wecode.security")
// PropertySource 默认取 application.properties
// @PropertySource(value = "config.properties")
public class SecurityProperties {

    /**
     * 超级管理员代码，超级管理员会忽略系统所有的功能权限检查和数据权限过滤
     */
    private String adminRoleCode = "System Admin";

    /**
     * 认证中心URL，存在SSO或者其他认证场景的配置该地址
     */
    private String authenticationHost;

    public String getAdminRoleCode() {
        return adminRoleCode;
    }

    public String getAuthenticationHost() {
        return authenticationHost;
    }


}
