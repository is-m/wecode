package com.chinasoft.it.wecode.annotations.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 操作
 * @author Administrator
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
@Inherited
public @interface Operate {

  /**
   * 功能代码
   * 
   * @return
   */
  String code() default "";

  /**
   * 功能说明
   * 
   * @return
   */
  String desc() default "";

  /**
   * 操作策略
   * 
   * @return
   */
  Policy policy() default Policy.Default;

  /**
   * 顺序(解析后，可以实现界面授权时的授权显示的顺序)
   * @return
   */
  int sort() default 9;

  public static enum Policy {
    /**
     * 所有人
     */
    All,
    /**
     * 能登录的用户
     */
    Login,
    /**
     * 在系统中具备有效角色的用户
     */
    Sys,
    /**
     * 基本检查，即假如一个请求经过多个权限检查的Method，第一个检查通过后，后续的无需再进行检查(碰到Required例外)
     */
    Default,
    /**
     * 当前权限必须严格检查
     */
    Required
  }
}
