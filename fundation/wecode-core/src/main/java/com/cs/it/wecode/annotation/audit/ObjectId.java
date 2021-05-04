package com.cs.it.wecode.annotation.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface ObjectId {

    /**
     * 审计属性名称
     *
     * @return
     */
    String value() default "";

}
