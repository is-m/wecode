package com.chinasoft.it.wecode.security.dto;

import com.chinasoft.it.wecode.annotations.excel.ExcelFiled;
import com.chinasoft.it.wecode.annotations.excel.ExcelSheet;
import com.chinasoft.it.wecode.common.dto.BaseDto;

import io.swagger.annotations.ApiModelProperty;

@ExcelSheet
public class UserResultDto extends BaseDto {

	private static final long serialVersionUID = 8070004604327615492L;

	/**
	 * id
	 */
	@ExcelFiled(title="ID")
	private String id;

	/**
	 * 姓名
	 */
	@ExcelFiled(title="姓名")
	private String name;

	/**
	 * 备注
	 */
	@ExcelFiled(title="备注")
	private String remark;

	/**
	 * 电子邮箱
	 */
	@ExcelFiled(title="电子邮件")
	private String mail;

	/**
	 * 移动号码 MP MobilePhone
	 */
	@ExcelFiled(title="手机号码")
	private String mobilePhone;

	/**
	 * 状态，0：失效，1：生效
	 */
	@ExcelFiled(title="状态",dictType="COMMON_STATUS")
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

	public UserResultDto() {
	}

	public UserResultDto(String id, String name, String remark, String mail, String mobilePhone, Integer status) {
		this.id = id;
		this.name = name;
		this.remark = remark;
		this.mail = mail;
		this.mobilePhone = mobilePhone;
		this.status = status;
	}

}
