package com.chinasoft.it.wecode.security.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.chinasoft.it.wecode.security.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

	int findCountByNameLikeAndStatusIn(String name, Integer status);

	List<User> findByNameLikeAndStatusIn(String name, Integer status, PageRequest pageRequest);

}
