package com.chinasoft.it.wecode.common.service;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 查询参数
 * 
 * 用于标注查询条件类上的参数
 * 
 * @author Administrator
 *
 */
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface Query {

	/**
	 * 查询参数类型，默认是相等
	 * 
	 * @return
	 */
	Type type() default Type.EQ;

	/**
	 * 字段，默认为当前注解所在字段名称，如果要人分指定（比如日期范围）则需要跟 entity class 中的field名称要一样
	 * 
	 * @return
	 */
	String field() default "";

	/**
	 * 校验分组
	 * 
	 * @return
	 */
	Class<?>[] groups() default { Default.class };

	/**
	 * 查询条件类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum Type {
		/**
		 * 相等,如果 值为集合或数组时，则查询条件应自动转换成 In
		 */
		EQ,
		/**
		 * 像，如果放在非字符串类型上是，保持跟EQ一个逻辑
		 */
		LK,
		/**
		 *  像，忽略大小写
		 */
		LKI,
		/**
		 * 大/等于
		 */
		GTE,
		/**
		 * 小/等于
		 */
		LTE,
	}

	public static interface Default {

	}

	public static interface First {

	}

	public static interface Second {

	}

}
