package com.chinasoft.it.wecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
@EnableJpaRepositories
public class SignApp {

  public static void main(String[] args) {
    SpringApplication.run(SignApp.class, args);
  }

}
