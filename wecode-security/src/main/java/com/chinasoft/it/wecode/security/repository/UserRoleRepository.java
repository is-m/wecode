package com.chinasoft.it.wecode.security.repository;

import com.chinasoft.it.wecode.security.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    Optional<UserRole> findFirstByUserId(String userId);

}
