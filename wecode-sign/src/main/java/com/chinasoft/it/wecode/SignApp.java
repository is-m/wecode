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

    // TODO：第一次启动时检查是否数据字典有进行过初始化，未初始化过时则进行初始化动作，所以所有数据字典都应该定义在被spring管理的bean里面，且具备默认值以便于初始化，对于url型的，需要增加扩展注解来辅助识别不同环境的不同配置
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
      // TODO:待实现代码提交时检查文件个数，或者代码行数，确认是否要拆分提交，提醒用户是否继续提交
      // FIXME:需要提醒开发人员按规则提交代码，例如一次做了Story 未提交那么需要按需选中提交3次，所以建议开发时一个story开发完了就提交一次
    }
  }

}
