package com.chinasoft.it.wecode.security.api;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.fw.spring.base.CrudApi;
import com.chinasoft.it.wecode.security.dto.PermissionDto;
import com.chinasoft.it.wecode.security.dto.PermissionQueryDto;
import com.chinasoft.it.wecode.security.dto.PermissionResultDto;
import com.chinasoft.it.wecode.security.service.impl.PermissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "权限API")
@RestController
@RequestMapping("/services/security/permission")
public class PermissionApi extends CrudApi<PermissionDto, PermissionResultDto, PermissionQueryDto> {

  private PermissionService service;

  @PostConstruct
  public void init() {
    service = (PermissionService) super.service;
  }

  @ApiOperation(value = "权限同步", notes = "自动识别系统权限")
  @PostMapping("/sync")
  public void sync() {
    service.sync();
  }

  @ApiOperation(value = "清理无效权限", notes = "清理无效权限")
  @DeleteMapping("/clearInvalid")
  public void clearInvalid() {
    service.clearInvalid();
  }

}
