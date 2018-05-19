package com.chinasoft.it.wecode.security.dto;

import com.chinasoft.it.wecode.common.dto.BaseDto;
import com.chinasoft.it.wecode.common.service.Query;
import com.chinasoft.it.wecode.common.service.Query.Type;

import io.swagger.annotations.ApiModelProperty;

public class RoleQueryDto extends BaseDto {

	private static final long serialVersionUID = 1667746108011113090L;

	/**
	 * 角色名
	 */
	@ApiModelProperty("角色名")
	@Query(type = Type.LK)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
