package com.chinasoft.it.wecode.sign.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class SignDto {

	/**
	 * sign user
	 */
	@NotEmpty
	private String userId;

	/**
	 * sign point
	 */
	@NotEmpty
	private String point;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	} 
}
