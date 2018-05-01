package com.chinasoft.it.wecode.annotations.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelFiled {

	/**
	 * 取值字段 （默认调用当前字段的get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”,“office.name”）
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * 显示标题 （需要添加批注请用“**”分隔，标题**批注，仅对导出模板有效）
	 * 
	 * @return
	 */
	String title() default "";

	/**
	 * 字段类型(0：导出导入；1：仅导出2：仅导入)
	 */
	int type() default FiledConstant.TYPE_DEFAULT;

	/**
	 * 导出字段对齐方式(0：自动；1：靠左；2：居中；3：靠右）
	 */
	int align() default FiledConstant.ALIGN_DEFAULT;

	/**
	 * 导出字段所用单元格默认宽度 3000
	 */
	int width() default FiledConstant.WIDTH_DEFAULT;

	/**
	 * 导出字段字段排序（升序）
	 */
	int sort() default FiledConstant.SORT_DEFAULT;

	/**
	 * 如果是字典类型，请设置字典的type
	 */
	String dictType() default "";

	/**
	 * 反射类型
	 */
	Class<?> fieldType() default Class.class;

	/**
	 * 字段归属组（根据分组导出导入）
	 */
	int[] groups() default {};

}
