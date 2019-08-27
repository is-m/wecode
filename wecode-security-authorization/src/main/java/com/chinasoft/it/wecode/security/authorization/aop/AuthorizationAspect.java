package com.chinasoft.it.wecode.security.authorization.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.chinasoft.it.wecode.annotations.security.Module;
import com.chinasoft.it.wecode.annotations.security.Operate;
import com.chinasoft.it.wecode.annotations.security.Operate.Policy;
import com.chinasoft.it.wecode.common.exception.UnSupportException;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.fw.spring.utils.AopUtil;
import com.chinasoft.it.wecode.security.authorization.aop.check.ContextCheck;
import com.chinasoft.it.wecode.security.authorization.aop.check.LoginCheck;
import com.chinasoft.it.wecode.security.authorization.aop.check.Check;
import com.chinasoft.it.wecode.security.authorization.aop.check.RealCheck;
import com.chinasoft.it.wecode.security.authorization.aop.check.RoleCheck;
import com.chinasoft.it.wecode.security.authorization.utils.AuthorizationUtil;
import com.chinasoft.it.wecode.security.service.impl.UserContextManager;

/**
 * 鉴权切面
 *
 * @author Administrator
 */
@Component
@Aspect
@Conditional(AuthorizationAspectCondition.class)
public class AuthorizationAspect {

    private static final Logger log = LogUtils.getLogger();

    // @Around("with(com.chinasoft.it.wecode.annotations.security.Module) && @annotation(operate)")
    @Around("@annotation(operate)")
    public Object around(ProceedingJoinPoint joinPoint, Operate operate) throws Throwable {
        Method method = AopUtil.getActualMethod(joinPoint);
        Class<?> classTarget = joinPoint.getTarget().getClass();
        Module annModule = classTarget.getAnnotation(Module.class);

        String cname = classTarget.getName();
        String mname = method.getName();
        Policy policy = operate.policy();
        if (UserContextManager.isSuperAdmin()) {
            log.debug("security method be skiped for Super Administrator");
        } else if (policy != Policy.All) {
            if (annModule == null) {
                // 不检查权限
                log.info("a security method is not badge @Module at {}.{}", cname, mname);
            } else {
                Check component = null;
                // 检查权限
                switch (policy) {
                    case Required:
                        component = new RealCheck(component);
                    case Default:
                        component = new ContextCheck(component);
                    case Sys:
                        component = new RoleCheck(component);
                    case Login:
                        component = new LoginCheck(component);
                        break;
                    default:
                        throw new UnSupportException();
                }
                component.check(AuthorizationUtil.getPermissionCode(annModule, cname, operate, mname));
            }
        }

        return joinPoint.proceed();
    }

}
