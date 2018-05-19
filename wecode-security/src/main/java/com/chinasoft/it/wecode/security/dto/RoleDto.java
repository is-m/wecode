package com.chinasoft.it.wecode.security.dto;

import org.hibernate.validator.constraints.Length;

import com.chinasoft.it.wecode.common.dto.BaseDto;

/**
 * 角色DTO
 * 
 * @author Administrator
 *
 */
public class RoleDto extends BaseDto {

	private static final long serialVersionUID = 8273374251998295800L;

	/**
	 * 角色名称
	 */
	@Length(max = 255)
	private String name;

	/**
	 * 角色代码
	 */
	@Length(max = 100)
	private String code;

	/**
	 * 角色描述
	 */
	@Length(max = 4000)
	private String remark;

	/**
	 * 角色首页
	 */
	@Length(max = 300)
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
