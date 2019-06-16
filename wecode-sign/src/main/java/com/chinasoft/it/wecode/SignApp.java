package com.chinasoft.it.wecode;

import com.chinasoft.it.wecode.common.util.ApplicationUtils;
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

    System.out.println("\r\n");
    if(ApplicationUtils.getEnvironment().acceptsProfiles("dev")) {
      System.out.println("-----------------------------------------------------");
      System.out.println("   Coding 8 荣 8 耻\r\n");
      System.out.println("以可配置为荣，以硬编码为耻 \n" +
              "以无状态为荣，以有状态为耻 \n" +
              "以标准化为荣，以特殊化为耻 \n" +
              "以整体交付为荣，以部分交付为耻 \n" +
              "以无人值守为荣，以人工介入为耻 \n" +
              "以系统互备为荣，以系统单点为耻 \n" +
              "以随时可重启为荣，以不能迁移为耻 \n" +
              "以自动化工具为荣，以人肉操作为耻 ");
      System.out.println("-----------------------------------------------------");
    }
  }

}
