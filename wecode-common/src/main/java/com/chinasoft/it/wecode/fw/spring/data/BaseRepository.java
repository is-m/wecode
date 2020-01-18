package com.chinasoft.it.wecode.fw.spring.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 通用的 Repositry 实现，如果要开启，请在@EnableJpaRepositories注解上添加baseClass属性
 * @author Administrator
 *
 * @param <T>
 * @param <ID>
 */
// @NoRepositoryBean 一般用作父类的repository，有这个注解，spring不会去实例化该repository
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

  // global

}
