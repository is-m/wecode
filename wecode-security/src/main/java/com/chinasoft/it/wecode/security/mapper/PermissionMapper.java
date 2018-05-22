package com.chinasoft.it.wecode.security.mapper;

import org.mapstruct.Mapper;

import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.security.domain.Permission;
import com.chinasoft.it.wecode.security.dto.PermissionDto;
import com.chinasoft.it.wecode.security.dto.PermissionResultDto;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface PermissionMapper extends BaseMapper<Permission, PermissionDto, PermissionResultDto> {

}
