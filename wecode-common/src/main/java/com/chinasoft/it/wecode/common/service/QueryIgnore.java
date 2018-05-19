package com.chinasoft.it.wecode.common.service;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 忽略查询
 * 
 * @author Administrator
 *
 */
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface QueryIgnore {

}
