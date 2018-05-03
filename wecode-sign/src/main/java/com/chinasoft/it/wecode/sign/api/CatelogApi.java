package com.chinasoft.it.wecode.sign.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.sign.dto.CatelogDto;
import com.chinasoft.it.wecode.sign.dto.CatelogMenuDto;
import com.chinasoft.it.wecode.sign.dto.CatelogQueryDto;
import com.chinasoft.it.wecode.sign.dto.CatelogResultDto;
import com.chinasoft.it.wecode.sign.service.impl.CatelogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Sign Api")
@RestController
@RequestMapping("/services/base/catelog")
public class CatelogApi {

	@Autowired
	private CatelogService service;

	@ApiOperation(value = "创建栏目", notes = "创建栏目")
	@PostMapping
	public CatelogResultDto create(@RequestBody CatelogDto catelogDto) {
		return service.create(catelogDto);
	}

	@ApiOperation(value = "修改栏目", notes = "修改栏目")
	@PutMapping("/{id}")
	public CatelogResultDto update(@PathVariable("id") String id,@RequestBody CatelogDto dto) {
		return service.update(id, dto);
	}

	@ApiOperation(value = "查询栏目（适用于管理界面）", notes = "查询栏目（适用于管理界面）")
	@GetMapping("/mng")
	public List<CatelogResultDto> findManageList(CatelogQueryDto dto){
		return service.findManageList(dto);
	}

	@ApiOperation(value = "查询栏目（适用于菜单数据源）", notes = "查询栏目（适用于菜单数据源）")
	@GetMapping("/menu")
	public List<CatelogMenuDto> findMenuList() {
		return service.findMenuList();
	}
	
	@ApiOperation(value = "查询栏目（适用于菜单数据源，信息较为完整也可以用于树形管理界面）", notes = "查询栏目（适用于菜单数据源，信息较为完整也可以用于树形管理界面）")
	@GetMapping("/tree")
	public List<CatelogResultDto> findTreeList() {
		return service.findTreeList();
	}
	
	@ApiOperation(value = "按ID删除栏目", notes = "按ID删除栏目，多个用 , 分割")
	@DeleteMapping
	public void delete(@RequestParam("ids") String ids) {
		service.delete(ids.split(","));
	}
}
