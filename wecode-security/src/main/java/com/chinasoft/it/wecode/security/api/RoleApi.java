package com.chinasoft.it.wecode.security.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.fw.spring.base.CrudApi;
import com.chinasoft.it.wecode.security.domain.Role;
import com.chinasoft.it.wecode.security.dto.RoleDto;
import com.chinasoft.it.wecode.security.dto.RoleQueryDto;
import com.chinasoft.it.wecode.security.dto.RoleResultDto;

import io.swagger.annotations.Api;

@Api(tags="角色API")
@RestController
@RequestMapping("/services/security/role")
public class RoleApi extends CrudApi<Role, RoleDto, RoleResultDto, RoleQueryDto> {

	
}
