package com.chinasoft.it.wecode.template.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.chinasoft.it.wecode.template.mybatis.dao"})
public class MybatisConfig {

  
}
