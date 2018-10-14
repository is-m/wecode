package com.chinasoft.it.wecode.common.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * 下面写法参考 http://www.sdky.org/news/2018-04-07/141497.html
 * {@code @Repeatable(TodoTypeValid.List.class)} 是 JDK8 支持的同一注解多次特性。
 * {@code
 *  @TodoTypeValid(value = {"0", "1", "2"}, message = "{todo.todoType.insert}", groups = {C.Insert.class, S.Insert.class})
 *  @TodoTypeValid(value = {"3", "4"}, message = "{todo.todoType.update}", groups = {C.Update.class, S.Update.class})
 *  private String todoType;
 * }
 * @author Administrator
 *
 */
@Documented
@Constraint(validatedBy = {Demo.DemoFactory.class})
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Demo.List.class) 
public @interface Demo {
  String message() default "请输入正确的类型";

  String[] value() default {};

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class DemoFactory implements ConstraintValidator<Demo, String> {
    private String[] annotationValue;

    @Override
    public void initialize(Demo todoStatusValid) {
      this.annotationValue = todoStatusValid.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      if (Arrays.asList(annotationValue).contains(value))
        return true;
      return false;
    }
  }
  @Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {
    Demo[] value();
  }
}
