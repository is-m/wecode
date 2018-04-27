package com.chinasoft.it.wecode.security.dto;

/**
 * 用户导出条件DTO（存在ids时，则按ids导出,否则按条件导出）
 * 
 * @author Administrator
 *
 */
public class UserExportDto extends UserQueryDto {

	private static final long serialVersionUID = -7359427963263632376L;

	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
