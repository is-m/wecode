package com.chinasoft.it.wecode.security.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chinasoft.it.wecode.common.util.PageConstants;
import com.chinasoft.it.wecode.common.util.WebUtils;
import com.chinasoft.it.wecode.excel.utils.ExcelServletUtils;
import com.chinasoft.it.wecode.security.dto.UserDto;
import com.chinasoft.it.wecode.security.dto.UserExportDto;
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
		return service.findPagedData(WebUtils.getPageable(request), dto);
	}

	@ApiOperation(value = "按ID查询单个用户", notes = "按ID查询单个用户")
	@GetMapping("/{id}")
	public UserResultDto findOne(@PathVariable("id") String id) {
		return service.findOne(id);
	}

	@ApiOperation(value = "按ID删除用户", notes = "按ID删除用户，多个用 , 分割")
	@DeleteMapping
	public void delete(@RequestParam("ids") String ids) {
		service.delete(ids.split(","));
	}

	@ApiOperation(value = "导出用户", notes = "导出用户")
	@GetMapping("/export")
	public ResponseEntity<String> export(HttpServletResponse resp, UserExportDto exportDto) throws Exception {
		ExcelServletUtils.setExcelResponse(resp, "UserList");
		service.export(exportDto, resp.getOutputStream());
		return ResponseEntity.ok("success");
	}

	// https://www.cnblogs.com/zuolijun/p/5644411.html
	@ApiOperation(value = "导入用户", notes = "导入用户")
	@PostMapping(value = "/import", consumes = "multipart/form-data", headers = "content-type=multipart/form-data", produces = "application/json"/* produces = "application/octet-stream"*/)
	public ResponseEntity<Map<String,Object>> imports(@RequestParam(value = "file") MultipartFile file) throws Exception {
		if (!file.isEmpty()) {
			service.imports(file.getInputStream());
		}
		Map<String,Object> result = new HashMap<>();
		result.put("success", true);
		return ResponseEntity.ok(result);
	}

	// https://stackoverflow.com/questions/30400477/how-to-open-local-files-in-swagger-ui
	// https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#relative-schema-file-example
}
