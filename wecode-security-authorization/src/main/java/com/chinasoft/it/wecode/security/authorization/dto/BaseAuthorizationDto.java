package com.chinasoft.it.wecode.security.authorization.dto;

import com.chinasoft.it.wecode.common.dto.BaseDto;
import com.chinasoft.it.wecode.security.authorization.utils.AuthorizationConstant;

public abstract class BaseAuthorizationDto extends BaseDto {

	private static final long serialVersionUID = -5096308486839668516L;

	public static final String SPERATOR = AuthorizationConstant.SPERATOR;

	/**
	 * 资源代码
	 */
	private String code;

	/**
	 * 资源描述
	 */
	private String desc;

	/**
	 * 权限代码
	 */
	private String permissionCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPermissionCode() {
		if (permissionCode == null) {
			permissionCode = permissionCode();
		}
		return permissionCode;
	}

	protected abstract String permissionCode();
}
