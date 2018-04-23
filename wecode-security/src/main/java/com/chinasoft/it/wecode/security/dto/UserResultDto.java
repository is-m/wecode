package com.chinasoft.it.wecode.security.dto;

import com.chinasoft.it.wecode.common.dto.BaseDto;

import io.swagger.annotations.ApiModelProperty;

public class UserResultDto extends BaseDto {

	private static final long serialVersionUID = 8070004604327615492L;
	
	/**
	 * id
	 */
	private String id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 电子邮箱
	 */
	private String mail;

	/**
	 * 移动号码 MP MobilePhone
	 */
	private String mobilePhone;

	/**
	 * 状态，0：失效，1：生效
	 */
	@ApiModelProperty(name = "status", notes = "状态，0：失效，1生效", example = "1")
	private Integer status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
