package com.chinasoft.it.wecode.security.dto;

import com.chinasoft.it.wecode.common.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户查询DTO")
public class UserQueryDto extends BaseDto {

	private static final long serialVersionUID = 6214035271820152091L;

	@ApiModelProperty(value = "name", notes = "用户名（模糊匹配）", example = "")
	private String name;

	@ApiModelProperty(value = "status", notes = "状态，0失效，1生效", example = "1")
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
