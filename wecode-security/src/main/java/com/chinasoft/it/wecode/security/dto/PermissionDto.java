package com.chinasoft.it.wecode.security.dto;

import java.util.Set;

import com.chinasoft.it.wecode.common.dto.BaseDto;

public class PermissionDto extends BaseDto {

	private static final long serialVersionUID = 8693754019244538478L;

	/**
	 * 父节点ID
	 */
	private String pid;

	/**
	 * 代码，通常等于SYS[类型标识]::Security.Role[模块]::create[功能]
	 */
	private String code;

	/**
	 * 简述
	 */
	private String note;

	/**
	 * 类型，module:模块,operate:操作
	 */
	private String type;

	/**
	 * 角色，放弃维护关系，由role维护
	 */
	private Set<RoleResultDto> roles;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<RoleResultDto> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleResultDto> roles) {
		this.roles = roles;
	}
}
