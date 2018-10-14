/*package com.chinasoft.it.wecode.common.validation.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.validation.ValidationProcessor;

*//**
 * 校验切面
 * @author Administrator
 *
 *//*
@Aspect
@Component
@Order(5)
public class ValidatorAspect {
  private static Logger logger = LogUtils.getLogger();

  @Around("controllerMethodPointcut()") // 指定拦截器规则；也可以直接把 “execution(* com.xjj.........)” 写进这里
  public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
    Method method = methodSignature.getMethod();
    Annotation[][] argAnnotations = method.getParameterAnnotations();
    Object[] args = pjp.getArgs();
    for (int i = 0; i < args.length; i++) {
      for (Annotation annotation : argAnnotations[i]) {
        if (Validated.class.isInstance(annotation)) {
          Validated validated = (Validated) annotation;
          Class<?>[] groups = validated.value();
          ValidationProcessor.valid(args[i], groups);
        }
      }
    }
    
    return pjp.proceed(args);
  }
}
*/