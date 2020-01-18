package com.chinasoft.it.wecode.security.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chinasoft.it.wecode.security.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

  int findCountByNameLikeAndStatus(String name, Integer status);

  List<User> findByNameLikeAndStatus(String name, Integer status, PageRequest pageRequest);

  @Query("select new User(u.id,u.password,u.status) from User u where u.name=?1 or u.mail=?1 or u.mobilePhone=?1")
  User findOneByNameOrMailOrMobilePhone(String identifier);

}
