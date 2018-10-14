package com.chinasoft.it.wecode.security.api;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.fw.spring.base.CrudApi;
import com.chinasoft.it.wecode.security.dto.RoleDto;
import com.chinasoft.it.wecode.security.dto.RoleQueryDto;
import com.chinasoft.it.wecode.security.dto.RoleResultDto;
import com.chinasoft.it.wecode.security.service.impl.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "角色API")
@RestController
@RequestMapping("/services/security/role")
public class RoleApi extends CrudApi<RoleDto, RoleResultDto, RoleQueryDto> {

  private RoleService service;

  @PostConstruct
  public void init() {
    service = (RoleService) super.service;
  }

  /**
   * 角色授权
   * 
   * @param promissions
   */
  @ApiOperation(value = "角色授权", notes = "绑定角色权限")
  @PostMapping("/{roleId}/permissions")
  public void boundPromissions(@PathVariable("roleId") String roleId, @RequestBody String[] promissions) {
    service.boundPermission(roleId, promissions);
  }

  /**
   * 角色授权
   * 
   * @param promissions
   */
  @ApiOperation(value = "查询角色功能权限ID列表", notes = "查询角色功能权限ID列表")
  @GetMapping("/{roleId}/permissionIds")
  public Set<String> rolePromissions(@PathVariable("roleId") String roleId) {
    return service.getPermissionIds(roleId);
  }
}
