package com.chinasoft.it.wecode.security.service;

import com.chinasoft.it.wecode.security.TokenContext;

public interface TokenService {

	/**
	 * 
	 * @param userIdentifier
	 * @param payload
	 * @return
	 */
	String build(TokenContext context);

	/**
	 * 
	 * @param token
	 * @return
	 */
	TokenContext parse(String token);

}
