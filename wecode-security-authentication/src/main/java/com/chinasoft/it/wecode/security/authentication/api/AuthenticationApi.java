package com.chinasoft.it.wecode.security.authentication.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.security.authentication.service.LoginService;
import com.chinasoft.it.wecode.security.authentication.service.impl.AuthenticationService;
import com.chinasoft.it.wecode.security.authentication.service.impl.TokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * token 要去认证中心获取，而非到每个模块获取，集成该模块
 * @author Administrator
 *
 */
@Api("认证")
@RestController
@RequestMapping("/security/authentication")
public class AuthenticationApi {

	@Autowired
	private AuthenticationService authenticationService; 

	private TokenService tokenService;

	@ApiOperation("获取token")
	@GetMapping("/token")
	public String getToken(@RequestParam(value = "identifier") String identifier,
			@RequestParam(value = "secret") String secret) {
		return tokenService.create(identifier, secret);
	}

	
}
