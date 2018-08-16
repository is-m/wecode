package com.chinasoft.it.wecode.security.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.wiring.BeanWiringInfoResolver;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.fw.spring.base.CrudApi;
import com.chinasoft.it.wecode.security.domain.Permission;
import com.chinasoft.it.wecode.security.dto.PermissionDto;
import com.chinasoft.it.wecode.security.dto.PermissionQueryDto;
import com.chinasoft.it.wecode.security.dto.PermissionResultDto;
import com.chinasoft.it.wecode.security.service.impl.PermissionService;

import io.swagger.annotations.Api;

@Api(tags = "权限API")
@RestController
@RequestMapping("/services/security/permission")
public class PermissionApi extends CrudApi<Permission, PermissionDto, PermissionResultDto, PermissionQueryDto> {

	private PermissionService service;

	@PostConstruct
	public void init() {
		service = (PermissionService) super.service;
	}

	@PostMapping("/sync")
	public void sync() {
		service.sync();
	}

	@DeleteMapping("/clearInvalid")
	public void clearInvalid() {
		service.clearInvalid();
	}

}
