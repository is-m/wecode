package com.chinasoft.it.wecode.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.security.domain.Role;
import com.chinasoft.it.wecode.security.dto.RoleDto;
import com.chinasoft.it.wecode.security.dto.RoleResultDto;

@Mapper(componentModel = "spring",uses = {PermissionMapper.class})
public interface RoleMapper extends BaseMapper<Role, RoleDto, RoleResultDto> {

	/**
	 * 忽略权限属性，应为仅少数接口需要权限属性，所以在需要使用权限属性时使用特别接口
	 */
	@Mappings({
		@Mapping(target="permissions",ignore=true)
	})
	RoleResultDto from(Role entity);
}
