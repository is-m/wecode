package com.chinasoft.it.wecode.fw.spring.mvc.log;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chinasoft.it.wecode.common.util.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

// http://sishuok.com/forum/posts/list/281.html
@Aspect
@Component
public class ServiceMethodLogAspect {

  private ObjectMapper om = new ObjectMapper();

  private static final Logger log = LoggerFactory.getLogger(ServiceMethodLogAspect.class);

  // usage sample @Before("controllerAspect()")
  @Pointcut("@annotation(com.annotation.SystemServiceLog)")
  public void serviceAspect() {}

  // @Around(value="@annotation(apiLog)") public Object around(ProceedingJoinPoint
  // pjp, ApiLog apiLog) {
  // @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
  @Before("@annotation(org.springframework.web.bind.annotation.GetMapping) " + "|| @annotation(org.springframework.web.bind.annotation.PostMapping)"
      + "|| @annotation(org.springframework.web.bind.annotation.PutMapping)" + "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)"
      + "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)")
  public void before(JoinPoint joinPoint) {
    // reflection
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String declaringTypeName = signature.getDeclaringTypeName();
    if ("org.springframework.boot.autoconfigure.web.BasicErrorController".equals(declaringTypeName)) {
      return;
    }

    Method method = signature.getMethod();

    HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    // arg
    Map<String, Object> argMap = new TreeMap<>();
    String[] argumentNames = signature.getParameterNames();
    for (int i = 0, j = joinPoint.getArgs().length; i < j; i++) {
      Object arg = joinPoint.getArgs()[i];
      argMap.put(argumentNames[i], arg == null ? "$null$" : (arg instanceof Serializable ? arg : arg.toString()));
    }

    String jsonParams = JSONUtils.getJson(argMap);
    ApiOperation api = method.getAnnotation(ApiOperation.class);
    String apiName = api == null || StringUtils.isEmpty(api.value()) ? method.getName() : api.value();

    log.info("API {}  calling of URI:{} JAVA:{}::{}  \r\nargs:{}", apiName, req.getRequestURI(), declaringTypeName, method.getName(), jsonParams);
  }
  /*
   * @Before("@annotation(org.springframework.web.bind.annotation.GetMapping)") public void
   * around(JoinPoint joinPoint) { System.out.println("有人在调用API方法啦"); }
   */
}
