package com.chinasoft.it.wecode.security.service;

/**
 * token 管理接口
 * 
 * @author Administrator
 *
 */
public interface TokenManagementService {

	/**
	 * 保存当前 token（通常每次生成完token后会调用该接口）
	 * 
	 * @param uid
	 * @param token
	 */
	void save(String uid, String token);

	/**
	 * 获取用户 token
	 * 
	 * @param uid
	 */
	void get(String uid);

	/**
	 * 删除用户 token
	 * 
	 * @param uid
	 */
	void remove(String uid);
}
