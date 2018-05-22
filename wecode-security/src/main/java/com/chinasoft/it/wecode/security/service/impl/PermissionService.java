package com.chinasoft.it.wecode.security.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.chinasoft.it.wecode.base.BaseService;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.security.domain.Permission;
import com.chinasoft.it.wecode.security.dto.PermissionDto;
import com.chinasoft.it.wecode.security.dto.PermissionResultDto;

@Service
public class PermissionService extends BaseService<Permission, PermissionDto, PermissionResultDto> {

	public PermissionService(JpaRepository<Permission, String> repository,
			BaseMapper<Permission, PermissionDto, PermissionResultDto> mapper) {
		super(repository, mapper, Permission.class);
	}

}
