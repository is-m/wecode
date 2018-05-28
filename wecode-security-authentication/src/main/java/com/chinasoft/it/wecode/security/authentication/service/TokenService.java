package com.chinasoft.it.wecode.security.authentication.service;

public interface TokenService {

	/**
	 * 保存当前 token（通常每次生成完token后会调用该接口）
	 * 
	 * @param userIdentifer
	 * @param token
	 */
	public void save(String userIdentifer, String token);

	/**
	 * 获取用户 token
	 * 
	 * @param userIdentifer
	 */
	public void get(String userIdentifer);

	/**
	 * 删除用户 token
	 * 
	 * @param userIdentifer
	 */
	public void remove(String userIdentifer);
}
