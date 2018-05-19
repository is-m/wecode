package com.chinasoft.it.wecode.security.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.chinasoft.it.wecode.base.BaseService;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.security.domain.Role;
import com.chinasoft.it.wecode.security.dto.RoleDto;
import com.chinasoft.it.wecode.security.dto.RoleResultDto;

@Service
public class RoleService extends BaseService<Role, RoleDto, RoleResultDto> {

	public RoleService(JpaRepository<Role, String> repository, BaseMapper<Role, RoleDto, RoleResultDto> mapper) {
		super(repository, mapper, Role.class);
	}

}
