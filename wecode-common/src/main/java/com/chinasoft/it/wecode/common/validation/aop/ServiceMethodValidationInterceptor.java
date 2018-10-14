package com.chinasoft.it.wecode.common.validation.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

import javax.validation.Valid;
import javax.validation.Validator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.validation.ValidationProcessor;

/**
 * 服务类加入自动校验的逻辑 TODO:暂时没有实现检查分组的内容识别
 * 
 * @author Administrator
 *
 */
@Aspect
@Component
@Order(5)
public class ServiceMethodValidationInterceptor {

  private static Logger logger = LogUtils.getLogger();
  
  @Autowired
  private Validator validator;

  // 更多AOP表达式写法，可以参考：https://www.cnblogs.com/rainy-shurun/p/5195439.html
  // https://blog.csdn.net/qq525099302/article/details/53996344
  @Before("within(com.chinasoft.it.wecode.**.*Service) && (@args(javax.validation.Valid) || @args(org.springframework.validation.annotation.Validated))")
  public void beforeInvorkValid(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Parameter[] parameters = signature.getMethod().getParameters();
    for (int i = 0; i < parameters.length; i++) {
      Parameter param = parameters[i];
      Annotation annotation = param.getAnnotation(Validated.class);
      if (annotation == null) {
        annotation = param.getAnnotation(Valid.class);
      }
      logger.info("{} anno {}", param.getName(), annotation);
      if (annotation != null) {
        Class<?>[] groups = null;
        if (annotation instanceof Validated) {
          Validated v = (Validated) annotation;
          groups = v.value();
        }
        ValidationProcessor.valid(args[i], groups);
      }
    }
  }


}
