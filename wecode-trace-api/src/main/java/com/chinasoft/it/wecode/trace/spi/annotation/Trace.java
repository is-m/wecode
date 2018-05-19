package com.chinasoft.it.wecode.trace.spi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(CLASS)
@Target(METHOD)
public @interface Trace {

	/**
	 * 类型
	 * 
	 * @return
	 */
	TraceType type();
	
	// TODO:可能需要一个线程中如果出现多次start，那么意味着需要确认是否是有意为之
	
	// TODO：多线程中如果存在Trace，可能需要多线程的动作对象添加一个属性traceId，并通过主线程塞进去

	public static enum TraceType {
		/**
		 * 开始节点
		 */
		start,
		/**
		 * 普通节点
		 */
		normal,
	};
}
