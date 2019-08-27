package com.chinasoft.it.wecode.security.repository;

import com.chinasoft.it.wecode.security.domain.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission,String> {

    /**
     * 根据ID删除
     * @param dels
     */
    int deleteByIdIn(List<String> dels);

    /**
     * 查询第一个用户权限对象
     * @param id
     * @return
     */
    Optional<UserPermission> findFirstByUserId(String id);
}
