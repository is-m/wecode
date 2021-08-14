package com.chinasoft.it.wecode.channeler.annotation;

import com.chinasoft.it.wecode.channeler.config.ChannelConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ChannelConfig.class)
public @interface EnableChanneler {

}
