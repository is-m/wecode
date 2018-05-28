package com.chinasoft.it.wecode.security.authorization.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * 受控资源
 * 
 * @author Administrator
 *
 */
public class ResourceDto extends BaseAuthorizationDto {

	private static final long serialVersionUID = 4034625696891072914L;

	/**
	 * 资源操作
	 */
	private Set<OperationDto> operations = new HashSet<>();

	public Set<OperationDto> getOperations() {
		return operations;
	}

	public void setOperations(Set<OperationDto> operations) {
		this.operations = operations;
	}

	public void addOperation(OperationDto operation) {
		operation.setResourceCode(this.getPermissionCode());
		this.getOperations().add(operation);
	}

	@Override
	public String permissionCode() {
		return String.format("SYS%s%s", SPERATOR, getCode());
	}

	public ResourceDto() {
	}

	public ResourceDto(String code, String desc) {
		setCode(code);
		setDesc(desc);
	}

	public ResourceDto(String code, String desc, String defualtValue) {
		setCode((code == null || code.trim().length() == 0) ? defualtValue : code);
		setDesc((desc == null || desc.trim().length() == 0) ? defualtValue : desc);
	}
}
