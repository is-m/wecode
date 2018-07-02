package com.chinasoft.it.wecode.security.authorization.aop;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 
 * @author Administrator
 *
 */
public class AuthorizationAspectCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {  
		return !ArrayUtils.contains(context.getEnvironment().getActiveProfiles(), "dev");
	}

}
