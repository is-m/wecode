package com.chinasoft.it.wecode.admin.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class PropertyResultDto extends PropertyDto {

	private static final long serialVersionUID = 2146394828431395066L;

	@ApiModelProperty(name = "ID")
	private String id;

	private String path;

	@ApiModelProperty(name = "子节点")
	private List<PropertyResultDto> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<PropertyResultDto> getChildren() {
		return children;
	}

	public void setChildren(List<PropertyResultDto> children) {
		this.children = children;
	}
}
