package com.chinasoft.it.wecode.security.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 权限
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_role")
public class Permission extends BaseEntity {

	private static final long serialVersionUID = -2078243822238438173L;

	private String code;
	
	private String remark;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
