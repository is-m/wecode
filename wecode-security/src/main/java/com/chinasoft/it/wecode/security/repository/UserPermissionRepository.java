package com.chinasoft.it.wecode.security.repository;

import com.chinasoft.it.wecode.security.domain.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission,String> {

    /**
     * 根据用户ID删除用户权限列表
     * @param userId
     * @return
     */
    int deleteByUserId(String userId);
}
