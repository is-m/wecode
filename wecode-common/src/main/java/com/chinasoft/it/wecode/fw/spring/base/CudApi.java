package com.chinasoft.it.wecode.fw.spring.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinasoft.it.wecode.base.IBaseService;
import com.chinasoft.it.wecode.common.dto.BaseDto;

import io.swagger.annotations.ApiOperation;

/**
 * C 创建 U 更新 D 删除
 * 
 * @author Administrator
 *
 * @param <E>
 * @param <D>
 * @param <R>
 */
public class CudApi<D extends BaseDto, R extends BaseDto> {

  @Autowired
  protected IBaseService<D, R> service;

  @ApiOperation(value = "创建", notes = "创建")
  @PostMapping
  public R doCreate(@RequestBody D dto) {
    return service.create(dto);
  }

  @ApiOperation(value = "修改", notes = "修改")
  @PutMapping("/{id}")
  public R doUpdate(@PathVariable("id") String id, @RequestBody D dto) {
    return service.update(id, dto);
  }

  @ApiOperation(value = "删除", notes = "删除")
  @DeleteMapping
  public void doDelete(@RequestParam("ids") String ids) {
    service.delete(ids.split(","));
  }

}
