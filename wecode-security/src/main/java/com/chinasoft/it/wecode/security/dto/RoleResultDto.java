package com.chinasoft.it.wecode.security.dto;

import java.util.Set;

/**
 * 角色结果Dto
 * 
 * @author Administrator
 *
 */
public class RoleResultDto extends RoleDto {

	private static final long serialVersionUID = -7907070576163420456L;

	/**
	 * 角色ID
	 */
	private String id;

	/**
	 * 角色权限列表，只有指定的service才加载该属性，默认不加载
	 */
	private Set<PermissionResultDto> permissions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<PermissionResultDto> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<PermissionResultDto> permissions) {
		this.permissions = permissions;
	}

}
