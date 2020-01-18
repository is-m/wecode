package com.chinasoft.it.wecode.sign.config;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.Assert;

import com.chinasoft.it.wecode.common.util.ServletContextHolder;
import com.chinasoft.it.wecode.common.util.ServletContextHolder.ISession;
import com.chinasoft.it.wecode.fw.spring.data.BaseRepositoryImpl;
import com.chinasoft.it.wecode.security.AuthenticationConstant;
import com.chinasoft.it.wecode.security.UserPrincipal;

/**
 * https://www.w3cschool.cn/jpaspec/jcqh1s4i.html jpa 教程
 * https://www.jianshu.com/p/88d1f0da72b2 jpa 配置
 * 
 * UserRepositryImpl 作为其他接口的动态代理的实现基类。具有全局的性质，即使没有继承它所有的动态代理类也会变成它。
 * FIXME:新项目时，要修改BasePackages参数(@EnableJpaRepositories)
 * @author Administrator
 *
 */
@Configuration
@EnableJpaRepositories(basePackages= {"com.chinasoft.it.wecode"}/*,repositoryBaseClass= BaseRepositoryImpl.class*/)
@EnableJpaAuditing
public class JPAConfig {

  /**
   * AuditorAware：用于审计，即实现
   * @return
   */
  @Bean
  public AuditorAware<Serializable> auditorProvider() {
    return new AuditorAware<Serializable>() {

      @Override
      public Optional<Serializable> getCurrentAuditor() {
        // 如果集成了Spring security 也可以通过下面的方式获取用户ID
        // Authentication authentication = SecurityContextHodler.getContext();
        //

        ISession session = ServletContextHolder.getSession();
        UserPrincipal user = null;
        if (session != null) {
          Object sessionUser = session.getOrDefault(AuthenticationConstant.SESSION_LOGIN_USER, null);
          if (sessionUser != null) {
            Assert.isInstanceOf(UserPrincipal.class, "session user is not implement UserPrincipal");
            user = (UserPrincipal) sessionUser;
          }
        }
        return Optional.of(user != null ? user.getUid() : null);
      }
    };
  }


}
