package com.chinasoft.it.wecode.security.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 用户角色
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_user_role")
public class UserRole extends BaseEntity {

	private static final long serialVersionUID = 2707844086472889794L;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 角色ID
	 */
	private String roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
