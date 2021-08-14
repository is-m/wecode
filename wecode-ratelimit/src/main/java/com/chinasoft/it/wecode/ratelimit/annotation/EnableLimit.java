package com.chinasoft.it.wecode.ratelimit.annotation;

import com.chinasoft.it.wecode.ratelimit.config.LimitConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({LimitConfig.class})
public @interface EnableLimit {
}
