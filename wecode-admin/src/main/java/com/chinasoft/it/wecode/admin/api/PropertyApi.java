package com.chinasoft.it.wecode.admin.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.admin.dto.PropertyDto;
import com.chinasoft.it.wecode.admin.dto.PropertyResultDto;
import com.chinasoft.it.wecode.admin.service.impl.PropertyService;
import com.chinasoft.it.wecode.common.util.PageConstants;
import com.chinasoft.it.wecode.common.util.WebUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "数据字典API")
@RestController
@RequestMapping("/services/base/property")
public class PropertyApi {

	@Autowired
	private PropertyService service;

	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping
	public PropertyResultDto create(@RequestBody PropertyDto dto) {
		return service.create(dto);
	} 
	
	@ApiOperation(value = "修改", notes = "修改")
	@PutMapping("/{id}")
	public PropertyResultDto update(@PathVariable("id") String id, @RequestBody PropertyDto dto) {
		return service.update(id, dto);
	}

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = PageConstants.PARAM_PAGE, value = "当前页", paramType = "query", dataType = "Long"),
			@ApiImplicitParam(name = PageConstants.PARAM_SIZE, value = "页大小", paramType = "query", dataType = "Long"), })
	@GetMapping
	public Page<PropertyResultDto> findByPage(HttpServletRequest request, PropertyDto condition) {
		return service.findByPage(WebUtils.getPageable(request), condition);
	}

	@ApiOperation(value = "查询全部", notes = "查询全部")
	@GetMapping("/all")
	public List<PropertyResultDto> findAll(PropertyDto condition) {
		return service.findAll(condition);
	}

	@ApiOperation(value = "查询单个系统属性", notes = "可以按路径查询单个系统属性")
	@GetMapping("/one")
	public PropertyResultDto findByPath(PropertyDto condition) {
		return service.findOne(condition);
	}

	@ApiOperation(value = "查询子节点", notes = "查询全部")
	@GetMapping("/findChildrenByPath")
	public List<PropertyResultDto> findChildrenByPath(
			@RequestParam(name = "parentPath", required = true) String parentPath,
			@RequestParam(name = "deepSearch", required = false, defaultValue = "false") Boolean deepSearch) {
		return service.findChildrenByPath(parentPath, deepSearch);
	}
	
	@ApiOperation(value = "查找数据字典（树形数据）", notes = "查找数据字典（树形数据）")
	@GetMapping("/tree")
	public List<PropertyResultDto> findPropertyTree() {
		return service.findPropertyTree();
	}
	
	@ApiOperation(value = "按ID删除字典", notes = "按ID删除字典，多个用 , 分割")
	@DeleteMapping
	public void delete(@RequestParam("ids") String ids) {
		service.delete(ids.split(","));
	}
}
