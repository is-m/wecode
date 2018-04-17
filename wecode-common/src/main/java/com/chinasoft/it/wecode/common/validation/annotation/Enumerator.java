package com.chinasoft.it.wecode.common.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.lang3.ArrayUtils;

import com.chinasoft.it.wecode.common.validation.annotation.Enumerator.EnumerableValidator;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumerableValidator.class)
@Documented
public @interface Enumerator {

	String[] value() default {};

	String[] stringValue() default {};

	int[] intValue() default {};

	/**
	 * 用来定义默认得消息模版, 当这个约束条件被验证失败的时候,通过此属性来输出错误信息.
	 * 
	 * @return
	 */
	String message() default "请提供正确的枚举值";

	/**
	 * 用于指定这个约束条件属于哪(些)个校验组
	 * 
	 * @return
	 */
	Class<?>[] groups() default {};

	/**
	 * Bean Validation API 的使用者可以通过此属性来给约束条件指定严重级别. 这个属性并不被API自身所使用.
	 * 
	 * @return
	 */
	Class<? extends Payload>[] payload() default {};

	public class EnumerableValidator implements ConstraintValidator<Enumerator, Object> {

		private String[] values = null;

		@Override
		public void initialize(Enumerator anno) {
			if (anno.value() != null && anno.value().length > 0) {
				values = anno.value();
			} else if (anno.stringValue() != null && anno.stringValue().length > 0) {
				values = anno.stringValue();
			} else if (anno.intValue() != null && anno.intValue().length > 0) {
				int[] temp = anno.intValue();
				values = new String[temp.length];
				for (int i = 0; i < temp.length; i++) {
					values[i] = Objects.toString(temp[i]);
				}
			} else {
				throw new IllegalArgumentException("枚举校验注解未初始化，请设置相关value 或者 stringValue 或者 intValue 中的任意一个属性");
			}
		}

		@Override
		public boolean isValid(Object value, ConstraintValidatorContext context) {
			return ArrayUtils.contains(values, value);
		}

	}
}
