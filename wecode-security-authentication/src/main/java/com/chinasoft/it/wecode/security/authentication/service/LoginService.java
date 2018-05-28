package com.chinasoft.it.wecode.security.authentication.service;

public interface LoginService {

	/**
	 * 登录
	 * 
	 * @param identifier
	 *            用户标识（也许是id，姓名，email，mobilePhone）
	 * @param secret
	 *            安全码（也许是加密后的密码，也许是手机认证码）
	 * @return
	 */
	public void login(String identifier, String secret);

	/**
	 * 登出
	 * 
	 * @return
	 */
	public void logout();
}
