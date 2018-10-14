package com.chinasoft.it.wecode.fw.spring;

/**
 * 自我注入，防止在接口存在AOP编程时出下嵌套增强失效的场景
 * 
 * 考虑将所有Service 内的自调用PUBLIC方法都通过该方式
 * @author Administrator
 *
 */
public interface SelfInjectAware {


  /**
   * 自我注入
   * @param o
   */
  void setSelf(Object o);
  
}
