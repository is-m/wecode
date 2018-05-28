package com.chinasoft.it.wecode.fw.spring.utils;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class AopUtil {

	/**
	 * 获取连接点上实现类的方法(据说 直接调用 signature.getMethod 是返回的接口方法)
	 * 
	 * @param joinPoint
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method getActualMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException, SecurityException {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String methodName = signature.getName();
		Class<?> classTarget = joinPoint.getTarget().getClass();
		Method objMethod = classTarget.getMethod(methodName, signature.getParameterTypes());
		return objMethod;
	}
}
