package com.chinasoft.it.wecode.annotations.security;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface Module {

	/**
	 * 模块代码
	 * 
	 * @return
	 */
	String code() default "";

	/**
	 * 模块说明
	 * 
	 * @return
	 */
	String desc() default "";

}
