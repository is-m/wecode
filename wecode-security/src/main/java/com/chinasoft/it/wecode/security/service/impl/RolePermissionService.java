package com.chinasoft.it.wecode.security.service.impl;

import com.chinasoft.it.wecode.security.domain.Permission;
import com.chinasoft.it.wecode.security.dto.PermissionResultDto;
import com.chinasoft.it.wecode.security.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {

    @Autowired
    private RolePermissionRepository repo;

    @Autowired
    private PermissionService permissionService;

    public Set<String> findPermissionCodeSetByRoleId(String roleId) {
        List<String> permissionIdList = repo.findPermissionIdListByRoleId(roleId);
        return permissionService.findAll(permissionIdList).parallelStream().map(PermissionResultDto::getId).collect(Collectors.toSet());
    }

}
