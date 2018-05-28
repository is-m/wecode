package com.chinasoft.it.wecode.security;

import java.util.List;

public interface User {

	/**
	 * 获取用户标识（用户名，id，手机，邮箱）
	 * 
	 * @return
	 */
	String getUid();

	/**
	 * 安全码(可能是加密后的密码，又或是手机认证代码)
	 * 
	 * @return
	 */
	String getSecret();

	/**
	 * 当前活动的角色
	 * 
	 * @return
	 */
	Role getActivedRole();

	/**
	 * 当前用户角色列表
	 * 
	 * @return
	 */
	List<Role> getRoles();

}
