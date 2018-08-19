package com.chinasoft.it.wecode.security.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;
import com.chinasoft.it.wecode.fw.hibernate.Dynamic;

/**
 * 权限
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_permission")
@Dynamic
public class Permission extends BaseEntity {

	private static final long serialVersionUID = -2078243822238438173L;

	/**
	 * 父节点ID
	 */
	private String pid;

	/**
	 * 代码，通常等于SYS[类型标识]::Security.Role[模块]::create[功能]
	 */
	private String code;

	/**
	 * 简述
	 */
	private String note;

	/**
	 * 类型，module:模块,operate:操作
	 */
	private String type;

	/**
	 * 角色，放弃维护关系，由role维护
	 */
	@ManyToMany(mappedBy = "permissions", cascade = CascadeType.ALL)
	private Set<Role> roles;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Permission() {

	}

	public Permission(String id) {
		super.setId(id);
	}

	public Permission(String pid, String code, String note) {
		this.pid = pid;
		this.code = code;
		this.note = note;
	}

	public Permission(String pid, String code, String note, String type) {
		this(pid, code, note);
		this.type = type;
	}

}
