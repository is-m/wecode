package com.chinasoft.it.wecode.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chinasoft.it.wecode.security.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
