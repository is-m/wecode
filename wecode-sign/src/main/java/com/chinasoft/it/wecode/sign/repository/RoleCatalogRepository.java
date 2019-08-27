package com.chinasoft.it.wecode.sign.repository;

import com.chinasoft.it.wecode.sign.domain.RoleCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleCatalogRepository extends JpaRepository<RoleCatalog,String> {

    @Query("select catalogId from RoleCatalog rc where rc.roleId=:roleId")
    List<String> findCatalogIdListByRoleId(String roleId);
}
