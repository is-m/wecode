package com.chinasoft.it.wecode.security.api;

import com.chinasoft.it.wecode.common.dto.BatchDto;
import com.chinasoft.it.wecode.common.dto.RewritableDto;
import com.chinasoft.it.wecode.security.domain.UserPermission;
import com.chinasoft.it.wecode.security.dto.UserPermissionDto;
import com.chinasoft.it.wecode.security.dto.UserPermissionRewriteDto;
import com.chinasoft.it.wecode.security.service.impl.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户权限API
 */
@RestController
@RequestMapping("/services/security")
public class UserPermissionApi {

    @Autowired
    private UserPermissionService service;

    @GetMapping("/user/{userId}/permissions")
    public List<UserPermissionDto> findUserPermissions(@PathVariable("userId") String userId) {
        return service.findUserPermissions(userId);
    }

    @PutMapping("/user/{userId}/permissions/batch")
    public void batchUserPermissions(@PathVariable("userId") String userId,@RequestBody BatchDto<UserPermissionDto> batchDto) {
        service.batchUserPermissions(userId, batchDto);
    }
}
