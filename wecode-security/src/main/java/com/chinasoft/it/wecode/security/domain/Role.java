package com.chinasoft.it.wecode.security.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 角色
 * 
 * 角色中有一个super_admin，该角色要求忽略所有数据权限和功能权限的检查内容
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 7328361790049683148L;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 角色代码
	 */
	private String code;

	/**
	 * 角色描述
	 */
	private String remark;

	/**
	 * 角色首页
	 */
	private String url;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
