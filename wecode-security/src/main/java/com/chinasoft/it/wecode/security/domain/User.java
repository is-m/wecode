package com.chinasoft.it.wecode.security.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 用户
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_user")
public class User extends BaseEntity {

	private static final long serialVersionUID = -8916749944303282591L;

	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 密码
	 */
	private String password;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	} 
}
