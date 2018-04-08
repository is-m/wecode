package com.chinasoft.it.wecode.security.domain;

import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

import javax.persistence.Entity;

/**
 * 数据范围
 * 
 * 数据范围中需要提供一个all_data，来实现自动忽略数据范围检查的内容
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_data_range")
public class DataRange extends BaseEntity {

	private static final long serialVersionUID = -1493345011927538551L;

	/**
	 * 范围名称
	 */
	private String name;

	/**
	 * 范围描述
	 */
	private String remark;

	/**
	 * 是否生效
	 */
	private boolean enabled;

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
