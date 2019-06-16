package com.chinasoft.it.wecode.common.util;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 应用工具
 */
@Component
@Lazy(false)
public class ApplicationUtils implements ApplicationContextAware {

    private static ApplicationContext ac;

    /**
     * 应用变量
     * TODO:这种时单机写法，后面分布式时需要通过其他方式提供实现（分布式共享区域，例如redis,zookeeper,database）
     */
    private static final Map<String,Object> application = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationUtils.ac = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ac;
    }

    public static <T> T getBean(Class<T> clz) {
        return ac.getBean(clz);
    }

    public static <T> T getBeanOfNullable(Class<T> clz) {
        try {
            return ac.getBean(clz);
        } catch (BeansException e) {
            return null;
        }
    }

    /**
     * 获取类型Bean集合
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> Collection<T> getBeansOfType(Class<T> clz) {
        return ac.getBeansOfType(clz).values();
    }

    public static <T> T getBean(Class<T> clz, String name) {
        return ac.getBean(clz, name);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return ac.getBeansWithAnnotation(annotationType);
    }

    /**
     * 获取环境信息，环境信息中可以获取系统的配置或者当前激活的profile等信息
     * @return
     */
    public static Environment getEnvironment(){
        return ac.getEnvironment();
    }


    /**
     * 获取应用全局变量读写对象
     * TODO:需要考虑过期方案
     * @return
     */
    public static Map<String,Object> getApplication(){
        return application;
    }

}
