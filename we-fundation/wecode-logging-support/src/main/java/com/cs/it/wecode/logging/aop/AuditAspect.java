package com.cs.it.wecode.logging.aop;

import com.cs.it.wecode.annotation.audit.Audit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 审计日志处理
 */
@Component
@Aspect
public class AuditAspect {

    @Around("@annotation(audit)")
    public Object intercept(ProceedingJoinPoint pjp, Audit audit) throws Throwable {

        Object result = pjp.proceed();

        return result;
    }
}
