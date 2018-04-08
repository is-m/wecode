package com.chinasoft.it.wecode.security.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 角色
 * 
 * 角色中有一个super_admin，该角色要求忽略所有数据权限和功能权限的检查内容
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 7328361790049683148L;

	private String name;
	
	private String remark;

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
	
	
}
