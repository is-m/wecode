package com.chinasoft.it.wecode.fw.spring;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SelfInjectAwareBeanProcesser implements BeanPostProcessor, ApplicationContextAware {

  private ApplicationContext context;

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }

  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof SelfInjectAware) {
      // 1.如果当前对象是AOP代理对象，直接注入,
      // 2.否则则通过context.getBean(beanName)获取代理对象并注入，此种方式不适合解决prototype Bean的代理对象注入
      ((SelfInjectAware) bean).setSelf(AopUtils.isAopProxy(bean) ? bean : context.getBean(beanName));
    }
    return bean;
  }

  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }
}
