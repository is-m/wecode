package com.chinasoft.it.wecode.security.repository;

import com.chinasoft.it.wecode.security.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, String> {

    @Query("select permissionId from RolePermission rp where rp.roleId = :roleId")
    List<String> findPermissionIdListByRoleId(@Param("roleId") String roleId);
}
