package com.chinasoft.it.wecode.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chinasoft.it.wecode.security.domain.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {

}
