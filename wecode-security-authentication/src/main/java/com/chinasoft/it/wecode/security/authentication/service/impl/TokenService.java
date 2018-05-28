package com.chinasoft.it.wecode.security.authentication.service.impl;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

	/**
	 * 
	 * @param userIdentifier
	 *            用户标识
	 * @param secret
	 *            安全码
	 * @return
	 */
	public String create(String userIdentifier, String secret) {
		return userIdentifier + secret;
	}

	public boolean valid(String token) {
		return true;
	}
}
