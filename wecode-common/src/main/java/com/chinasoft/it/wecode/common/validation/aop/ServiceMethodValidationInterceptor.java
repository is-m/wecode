package com.chinasoft.it.wecode.common.validation.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

import javax.validation.Valid;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
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

	// 更多AOP表达式写法，可以参考：https://www.cnblogs.com/rainy-shurun/p/5195439.html
	@Before("within(com.chinasoft.it.wecode.**.*Service) && @args(javax.validation.Valid) &&  @annotation(anno)")
	public void beforeInvork(JoinPoint joinPoint, Valid anno) {
		System.err.println("Validator begin @Valid");
		valid(joinPoint, Valid.class);
	}

	@Before("within(com.chinasoft.it.wecode.**.*Service) && @args(org.springframework.validation.annotation.Validated) &&  @annotation(anno)")
	public void beforeInvork(JoinPoint joinPoint, Validated anno) {
		System.err.println("Validator begin @Validated");
		valid(joinPoint, Validated.class);
	}

	/**
	 * 校验接入点
	 * 
	 * @param joinPoint
	 */
	private void valid(JoinPoint joinPoint, Class<? extends Annotation> anno) {
		Object[] args = joinPoint.getArgs();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Parameter[] parameters = signature.getMethod().getParameters();
		for (int i = 0; i < parameters.length; i++) {
			Parameter param = parameters[i];
			Annotation annotation = param.getAnnotation(anno);
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
