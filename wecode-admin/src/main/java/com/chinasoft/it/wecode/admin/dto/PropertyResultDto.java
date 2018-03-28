package com.chinasoft.it.wecode.admin.dto;

import io.swagger.annotations.ApiModelProperty;

public class PropertyResultDto extends PropertyDto {

	@ApiModelProperty(name = "ID")
	private String id;
	
	private String path;  

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

}
