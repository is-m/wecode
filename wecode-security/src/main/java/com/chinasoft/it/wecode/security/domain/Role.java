package com.chinasoft.it.wecode.security.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;
import com.chinasoft.it.wecode.fw.hibernate.Dynamic;

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
@Dynamic
public class Role extends BaseEntity {

	private static final long serialVersionUID = 7328361790049683148L;

	/**
	 * 角色名称
	 */
	//@Indexed(unique=true, direction= IndexDirection.DESCENDING, dropDups=true)
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

	/**
	 * 状态,1：生效，0：失效
	 */
	private Integer status;

	@JoinTable(name = "sys_role_permission", joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "permission_id", referencedColumnName = "ID") })
	@ManyToMany(cascade=CascadeType.ALL)
	private Set<Permission> permissions;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

}
