package com.chinasoft.it.wecode.security.authorization.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.security.authorization.dto.ResourceDto;
import com.chinasoft.it.wecode.security.authorization.service.impl.PermissionFinderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("鉴权API")
@RestController
@RequestMapping("/services/security/authorization")
public class AuthorizationApi {

	@Autowired
	private PermissionFinderService service;

	@ApiOperation("发现权限")
	@GetMapping("/scan")
	public List<ResourceDto> scan() {
		return service.scan();
	}
	
	
}
