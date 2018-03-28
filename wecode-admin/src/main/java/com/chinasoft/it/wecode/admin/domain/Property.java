package com.chinasoft.it.wecode.admin.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 系统属性（数据字典）
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_property")
public class Property extends BaseEntity {

	private static final long serialVersionUID = 2035912199990173301L;

	/**
	 * parent id
	 */
	private String pid;

	/**
	 * name
	 */
	private String name;

	/**
	 * value
	 */
	private String value;

	/**
	 * remark
	 */
	private String remark;

	/**
	 * path
	 */
	private String path;

	/**
	 * sorted field
	 */
	private Long seq;
	
	/**
	 * version
	 */
	@Version
	private long version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}
