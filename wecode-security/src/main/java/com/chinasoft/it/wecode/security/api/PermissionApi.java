package com.chinasoft.it.wecode.security.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.fw.spring.base.CrudApi;
import com.chinasoft.it.wecode.security.domain.Permission;
import com.chinasoft.it.wecode.security.dto.PermissionDto;
import com.chinasoft.it.wecode.security.dto.PermissionQueryDto;
import com.chinasoft.it.wecode.security.dto.PermissionResultDto;

import io.swagger.annotations.Api;

@Api(tags="权限API")
@RestController
@RequestMapping("/services/security/permission")
public class PermissionApi extends CrudApi<Permission, PermissionDto, PermissionResultDto, PermissionQueryDto> {

	
}
