package com.chinasoft.it.wecode.security.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.security.dto.RoleResultDto;
import com.chinasoft.it.wecode.security.service.impl.UserRoleService;

import io.swagger.annotations.Api;

/**
 * 用户角色API
 * @author Administrator
 *
 */
@Api("用户角色API")
@RestController
@RequestMapping("/services/security")
public class UserRoleApi {

  @Autowired
  private UserRoleService service;

  /**
   * 获取用户角色
   * @return
   */
  @GetMapping("/user/{userId}/roles")
  public List<RoleResultDto> findUserRoles(@PathVariable("userId") String userId) {
    return service.findUserRoles(userId);
  }

  /**
   * 用户添加角色
   * @param userId 需要添加角色的用户ID
   * @param roleIdArray 角色ID列表
   */
  @PostMapping("/user/{userId}/roles")
  public void addUserRole(@PathVariable("userId") String userId, @RequestBody String[] roleIdArray) {
    service.addUserRoles(userId, roleIdArray);
  }

  /**
   * 获取用户可选的角色列表
   */
  @GetMapping("/user/{userId}/selectableRoles")
  public List<RoleResultDto> findUserSelectableRoles(@PathVariable("userId") String userId, @RequestParam("roleName") String roleName) {
    return null;
  }
}
