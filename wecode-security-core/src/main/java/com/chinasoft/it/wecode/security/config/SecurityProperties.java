package com.chinasoft.it.wecode.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wecode.security")
// PropertySource 默认取 application.properties
// @PropertySource(value = "config.properties")
public class SecurityProperties {

	private String authenticationHost;

	public String getAuthenticationHost() {
		return authenticationHost;
	}

}
