package com.chinasoft.it.wecode.fw.spring.support;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * https://developer.jboss.org/thread/278822?_sscc=t
 * https://www.jb51.net/article/143472.htm
 * 
 * 下面异常是因为（父类/接口）方法上无@Validated注解，实现类/派生类有注解，如要要解决需要将子类的注解移入父类或接口中
 * javax.validation.ConstraintDeclarationException: HV000151: A method overriding another method must not redefine the parameter constraint configuration, but method BaseService#update(String, BaseDto) redefines the configuration of IBaseService#update(String, BaseDto).
 * 
 * @author Administrator
 *
 */
@Configuration
public class BeanValidationConfiguration {

 /* @Bean
  public BeanPostProcessor beanValidationPostProcessor() {
    return new BeanValidationPostProcessor();
    
  }*/
  
  public BeanPostProcessor methodValidationPostProcessor() {
    return new MethodValidationPostProcessor();
  }
  
  
}
