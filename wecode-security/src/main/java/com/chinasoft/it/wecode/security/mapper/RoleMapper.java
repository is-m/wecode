package com.chinasoft.it.wecode.security.mapper;

import org.mapstruct.Mapper;

import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.security.domain.Role;
import com.chinasoft.it.wecode.security.dto.RoleDto;
import com.chinasoft.it.wecode.security.dto.RoleResultDto;

@Mapper(componentModel = "spring",uses = {PermissionMapper.class})
public interface RoleMapper extends BaseMapper<Role, RoleDto, RoleResultDto> {

}
