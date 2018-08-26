package com.chinasoft.it.wecode.security.service.impl;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.chinasoft.it.wecode.annotations.security.Module;
import com.chinasoft.it.wecode.annotations.security.Operate;
import com.chinasoft.it.wecode.base.BaseService;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.common.util.CollectionUtils;
import com.chinasoft.it.wecode.security.domain.Permission;
import com.chinasoft.it.wecode.security.domain.Role;
import com.chinasoft.it.wecode.security.dto.RoleDto;
import com.chinasoft.it.wecode.security.dto.RoleResultDto;

// TODO: @Cacheable 用于标记该类启动通用缓存处理
@Module(code = "role", desc = "role")
@Service
public class RoleService extends BaseService<Role, RoleDto, RoleResultDto> {

	@Autowired
	private PermissionService permissionService;

	public RoleService(JpaRepository<Role, String> repository, BaseMapper<Role, RoleDto, RoleResultDto> mapper) {
		super(repository, mapper, Role.class);
	}

	@Operate(code = "boundPermissin", desc = "boundPermissin")
	public void boundPermission(String roleId, String... permissionIds) {
		Assert.hasText(roleId, "role id cannot be null or empty");

		Role current = repo.findOne(roleId);

		// 先清空角色权限
		if (CollectionUtils.notEmpty(current.getPermissions())) {
			current.setPermissions(null);
			repo.save(current);
		}

		// 权限不为空时，添加角色权限
		if (permissionIds != null && permissionIds.length > 0) { 
			Set<Permission> permissions = permissionService.findAll(Arrays.asList(permissionIds)).parallelStream()
					.map(permissionService.getMapper()::result2Entity).collect(Collectors.toSet());
			current.setPermissions(permissions);
			repo.save(current);
		}
	}

	@Operate(code = "getRolePermissions", desc = "getRolePermissions")
	public Set<String> getPermissionIds(@NotEmpty String roleId) {
		return repo.findOne(roleId).getPermissions().parallelStream().map(Permission::getId)
				.collect(Collectors.toSet());
	}

}
