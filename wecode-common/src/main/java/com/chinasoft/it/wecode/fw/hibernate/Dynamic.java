package com.chinasoft.it.wecode.fw.hibernate;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

/**
 * 动态创建和更新
 * 
 * @DynamicInsert 不将 NULL 值字段插入数据库（可使默认属性生效）
 * @DynamicUpdate 不将 NULL 值更新数据库,使用该注解时须同时指定@SelectBeforeUpdate
 * @SelectBeforeUpdate 更新前先查询一次，比较有差异后再进行更新
 * @author Administrator
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
public @interface Dynamic {

}
