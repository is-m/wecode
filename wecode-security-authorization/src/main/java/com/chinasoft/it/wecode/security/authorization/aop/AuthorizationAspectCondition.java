package com.chinasoft.it.wecode.security.authorization.aop;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 开放认证拦截的条件
 * @author Administrator
 *
 */
public class AuthorizationAspectCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    return true;
    // return !ArrayUtils.contains(context.getEnvironment().getActiveProfiles(), "dev");
  }

}
