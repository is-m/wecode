package com.chinasoft.it.wecode.security.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.common.util.PageConstants;
import com.chinasoft.it.wecode.common.util.WebUtils;
import com.chinasoft.it.wecode.security.dto.UserDto;
import com.chinasoft.it.wecode.security.dto.UserQueryDto;
import com.chinasoft.it.wecode.security.dto.UserResultDto;
import com.chinasoft.it.wecode.security.service.impl.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("用户API")
@RestController
@RequestMapping("/services/security/user")
public class UserApi {

	@Autowired
	private UserService service;

	@ApiOperation(value = "创建用户", notes = "创建用户")
	@PostMapping
	public UserResultDto create(@RequestBody UserDto dto) {
		return service.create(dto);
	}

	@ApiOperation(value = "修改用户", notes = "修改用户")
	@PutMapping("/{id}")
	public UserResultDto update(@PathVariable("id") String id, @RequestBody UserDto dto) {
		return service.update(id, dto);
	}

	@GetMapping
	@ApiOperation(value = "查询用户列表（分页）", notes = "查询用户列表（分页）")
	@ApiImplicitParams({
			@ApiImplicitParam(name = PageConstants.PARAM_PAGE, value = "当前页", paramType = "query", dataType = "Long"),
			@ApiImplicitParam(name = PageConstants.PARAM_SIZE, value = "页大小", paramType = "query", dataType = "Long"), })
	public Page<UserResultDto> findPagedList(HttpServletRequest request, UserQueryDto dto) {
		return service.findPagedList(WebUtils.getPageable(request), dto);
	}

	@ApiOperation(value = "按ID查询单个用户", notes = "按ID查询单个用户")
	@GetMapping("/{id}")
	public UserResultDto findOne(@PathVariable("id") String id) {
		return service.findOne(id);
	}
}
