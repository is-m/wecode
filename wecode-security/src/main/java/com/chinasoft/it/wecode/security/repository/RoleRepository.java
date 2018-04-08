package com.chinasoft.it.wecode.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chinasoft.it.wecode.security.domain.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
