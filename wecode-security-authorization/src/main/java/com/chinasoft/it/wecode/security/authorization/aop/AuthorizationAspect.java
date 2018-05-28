package com.chinasoft.it.wecode.security.authorization.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.chinasoft.it.wecode.annotations.security.Module;
import com.chinasoft.it.wecode.annotations.security.Operate;
import com.chinasoft.it.wecode.annotations.security.Operate.Policy;
import com.chinasoft.it.wecode.common.exception.UnSupportException;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.fw.spring.utils.AopUtil;
import com.chinasoft.it.wecode.security.authorization.utils.AuthorizationUtil;

/**
 * 鉴权切面
 * 
 * @author Administrator
 *
 */
@Component
@Aspect
public class AuthorizationAspect {

	private static final Logger log = LogUtils.getLogger();

	@Around("@annotation(operate)")
	public void around(ProceedingJoinPoint joinPoint, Operate operate) throws Throwable {
		Method method = AopUtil.getActualMethod(joinPoint);
		Class<?> classTarget = joinPoint.getTarget().getClass();
		Module annModule = classTarget.getAnnotation(Module.class);

		String cname = classTarget.getName();
		String mname = method.getName();
		Policy policy = operate.policy();
		if (policy != Policy.All) {
			if (annModule == null) {
				// 不检查权限
				log.warn("a security method is not badge @Module at {}.{}", cname, mname);
			} else {
				
				// 检查权限
				switch (policy) {
				case Login:
					// 获取用户认证信息
					
					break;
				case Sys:
					// 获取用户角色

					break;
				case Default:
					// 获取当前检查栈

					break;
				case Required:
					// 直接检查

					break;
				default:
					throw new UnSupportException();
				}
				// 获取当前需要检查权限的权限代码
				String code = AuthorizationUtil.getPermissionCode(annModule, cname, operate, mname);

			}
		}
		try {
			joinPoint.proceed();
		} catch (Throwable e) {
			throw e;
		}
	}

}
