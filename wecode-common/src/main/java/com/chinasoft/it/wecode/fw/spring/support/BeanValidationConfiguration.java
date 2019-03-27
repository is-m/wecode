package com.chinasoft.it.wecode.fw.spring.support;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * https://developer.jboss.org/thread/278822?_sscc=t
 * https://www.jb51.net/article/143472.htm
 * 
 * 下面异常是因为（父类/接口）方法上无@Validated注解，实现类/派生类有注解，如要要解决需要将子类的注解移入父类或接口中
 * javax.validation.ConstraintDeclarationException: HV000151: A method overriding another method must not redefine the parameter constraint configuration, but method BaseService#update(String, BaseDto) redefines the configuration of IBaseService#update(String, BaseDto).
 * 
 * 校验顺序(@GroupSequence)
 * https://stackoverflow.com/questions/30039139/java-bean-validation-multiple-groupsequence-and-class-level-constraint
 * 
 * 自定义校验 （GroupSequenceProvider）
 * https://blog.csdn.net/qq_17586821/article/details/85134714
 * @author Administrator
 *
 */
@Configuration
public class BeanValidationConfiguration {

 /* @Bean
  public BeanPostProcessor beanValidationPostProcessor() {
    return new BeanValidationPostProcessor();
    
  }*/
  
  @Bean
  public BeanPostProcessor methodValidationPostProcessor() {
    return new MethodValidationPostProcessor(); 
  }
  
  @Bean
  public Validator validator(){
      ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
              .configure()
              // hibernate.validator.fail_fast true/false
              .addProperty("hibernate.validator.fail_fast", "false")
              .buildValidatorFactory();
      
      return validatorFactory.getValidator(); 
  }
  
}
