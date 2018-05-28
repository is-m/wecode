package com.chinasoft.it.wecode.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chinasoft.it.wecode.common.util.StringUtil;
import com.chinasoft.it.wecode.security.User;
import com.chinasoft.it.wecode.security.config.SecurityProperties;
import com.chinasoft.it.wecode.security.service.UserProvider;

import io.jsonwebtoken.lang.Assert;

/**
 * 网络用户提供程序（保证用户数据唯一来源）
 * 
 * @author Administrator
 *
 */
@Service
public class NetUserProvider implements UserProvider {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SecurityProperties properties;

	@Override
	public User get(String uid) {
		Assert.isTrue(StringUtil.isEmpty(properties.getAuthenticationHost()), "auth host no configured.");
		return null;
	}

}
