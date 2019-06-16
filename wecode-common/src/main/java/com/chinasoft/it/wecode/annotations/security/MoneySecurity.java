package com.chinasoft.it.wecode.annotations.security;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 财产安全，用于具备系统支付财产的接口，因为此类接口较容易被攻击，或者防止用户自己异常的调用拦截
 *
 * 使用场景：系统自动支付，系统推送短信
 *
 * 当标记在类上时，表示该类所有接口均需要执行严格的安全检查
 * 当标记在方法上时，表示方法需要执行严格的安全检查
 *
 * TODO：标记了该注解的方法，需要实现，IP，参数，在指定时间内被访问1或者多次，幂等，等用户自定义规则的拦截，实现可控制可定义的用户财产保全设置，待实现
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface MoneySecurity {

}
