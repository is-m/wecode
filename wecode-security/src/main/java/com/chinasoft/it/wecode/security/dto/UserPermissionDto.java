package com.chinasoft.it.wecode.security.dto;

import java.util.List;

import com.chinasoft.it.wecode.security.domain.DataRange;
import com.chinasoft.it.wecode.security.domain.Role;

public class UserPermissionDto {

	private String userId;

	private List<Role> roles;

	private List<DataRange> ranges;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<DataRange> getRanges() {
		return ranges;
	}

	public void setRanges(List<DataRange> ranges) {
		this.ranges = ranges;
	}
	
	
}
