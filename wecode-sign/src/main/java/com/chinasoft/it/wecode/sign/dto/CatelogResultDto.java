package com.chinasoft.it.wecode.sign.dto;

import java.util.List;

import com.chinasoft.it.wecode.common.dto.BaseDto;

public class CatelogResultDto extends BaseDto {

	private static final long serialVersionUID = 8134325944448275542L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 父节点ID
	 */
	private String pid;
	
	/**
	 * 父节点名称
	 */
	private String parentName;

	/**
	 * 栏目名称
	 */
	private String name;

	/**
	 * 栏目短路径
	 */
	private String path;

	/**
	 * 栏目完整路径
	 */
	private String fullPath;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 顺序
	 */
	private Integer seq;

	/**
	 * 状态：0 失效，1 生效
	 */
	private Integer status;

	/**
	 * 允许访问类型，all:全部可见，children:有子栏目时可见,authority:有权限时可见
	 */
	private String allowType;

	/**
	 * 允许访问值，在访问类型为有权限可见时应该需要设置该值,通常用于绑定功能权限
	 */
	private String allowValue;
	

	/**
	 * 目标类型，page:站内跳转(默认)，window：新页面
	 */
	//private String target;

	/**
	 * 子栏目
	 */
	private List<CatelogResultDto> children;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<CatelogResultDto> getChildren() {
		return children;
	}

	public void setChildren(List<CatelogResultDto> children) {
		this.children = children;
	}

	public String getAllowType() {
		return allowType;
	}

	public void setAllowType(String allowType) {
		this.allowType = allowType;
	}

	public String getAllowValue() {
		return allowValue;
	}

	public void setAllowValue(String allowValue) {
		this.allowValue = allowValue;
	}

	/*public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
*/
	
}
