package com.chinasoft.it.wecode.common.lock.intercept;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.chinasoft.it.wecode.annotations.concurrent.Lock;
import com.chinasoft.it.wecode.annotations.concurrent.LockContextProvider;
import com.chinasoft.it.wecode.common.lock.LockAccessException;
import com.chinasoft.it.wecode.common.lock.LockTimeoutException;
import com.chinasoft.it.wecode.common.lock.provider.LockProvider;
import com.chinasoft.it.wecode.common.util.LogUtils;


@Component
@Aspect
public class LockAnnotationBeanProcessor {

  private static final Logger log = LogUtils.getLogger();

  /**
   * 最大等待重试次数（FIXME:这个数值是未经过数据淬炼的，所以可能不是相对优的数值）
   */
  private static final short TIMEOUT_RETRY_COUNT_MAX = 10;

  /**
   * 最大顺延次数（FIXME:这个数值是未经过数据淬炼的，所以可能不是相对优的数值）
   */
  private static final short POSTPONE_COUNT_MAX = 50;

  @Autowired(required = false)
  private LockProvider lockProvider;

  private LockKeyExpressionEvaluator<Long> evaluator = new LockKeyExpressionEvaluator<>();


  /**
   * 获取锁Key
   * @param pjp
   * @param lock
   * @return
   */
  private String lockKey(ProceedingJoinPoint pjp, Lock lock) {
    String key = lock.value();
    MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
    if (StringUtils.isEmpty(key) || "default".equalsIgnoreCase(key)) {
      key = pjp.getTarget().getClass().getName() + ":" + methodSignature.getName();
    }

    // 如果存在 # 号表示是表达式
    if (key.contains("#")) {

    }

    return key;
  }

  /**
   * 获取上下文提供程序实例
   * @param lock
   * @param key
   * @return
   */
  private LockContextProvider getContextProvider(Lock lock, String key) {
    if (lock.contextProvider().isAssignableFrom(LockContextProvider.class)) {
      try {
        return (LockContextProvider) lock.contextProvider().newInstance();
      } catch (Exception e) {
        log.error("lock '" + key + "' lockContextProvider cannot be instance", e);
      }
    }
    return null;
  }

  /**
   * 获取透传参数
   * @param provider
   * @return
   */
  private Map<String, Object> resolveExtra(LockContextProvider provider) {
    Map<String, Object> result = provider != null ? provider.getExtra() : Collections.emptyMap();
    // FIXME 这里可以封装公共的透传参数，例如请求上下文，用户信息，等，如果有必要的话
    return result;
  }

  /**
   * 锁注解拦截处理
   * @param pjp
   * @param lock
   * @return
   * @throws Throwable
   */
  @Around("@annotation(lock)")
  public Object intercept(ProceedingJoinPoint pjp, Lock lock) throws Throwable, LockAccessException, LockTimeoutException {
    String key = lockKey(pjp, lock);
    OptionalLong lockValue = lockProvider.lock(key, lock.timeoutInMilliseconds());
    long timeouts = lockProvider.resolveTimeoutInMilliseconds(lock.timeoutInMilliseconds());

    try {
      // 如果未获取到锁
      if (!lockValue.isPresent()) {
        log.info("lock '{}' get lock is busy", key);
        // 乐观锁时，进行乐观锁的相关处理
        if (lock.optimistic()) {
          // 检查是否需要直接抛出异常
          if (lock.optimisticThrowable()) {
            throw new LockAccessException(String.format("lock '%s' [optimistic] is be used", key));
          }
          // 否则不执行锁住的业务操作
          return null;
        }

        // 悲观锁时，进行线程休眠等待
        short retryCount = 0;
        // 超时时长/5（FIXME:这个数值是未经过数据淬炼的，所以可能不是相对优的数值）
        long waitSleepMilliseconds = timeouts / 5;
        while (!lockValue.isPresent() && retryCount++ < TIMEOUT_RETRY_COUNT_MAX) {
          log.debug("lock '{}' busy of retry count {} in milliseconds {}", key, retryCount, waitSleepMilliseconds);
          TimeUnit.MICROSECONDS.sleep(waitSleepMilliseconds);
          lockValue = lockProvider.lock(key, lock.timeoutInMilliseconds());
        }
      }

      log.info("lock '{}' get successful", key);

      LockContextProvider contextProvider = getContextProvider(lock, key);

      // 主线程中获取透传参数
      Map<String, Object> extra = resolveExtra(contextProvider);

      // 执行任务
      Future<Object> future = CompletableFuture.supplyAsync(() -> {
        try {

          if (contextProvider != null) { // 参数透传初始化新线程的上下文
            contextProvider.init(extra);
          }

          Object result = pjp.proceed();

          if (contextProvider != null) { // 考虑到业务方法执行完成了可能事物已经提交了或者什么鬼，所以这里上行文释放失败，也只提示警告
            try {
              contextProvider.destroy();
            } catch (Throwable e) {
              log.warn("lock '" + key + "' handle completed but contextProvider.destory is faild , please check has threadlocal memery overflow ", e);
            }
          }

          return result;
        } catch (Throwable e) {
          return e;
        }
      });

      // 如果开启了顺延锁的占用
      if (lock.autoPostpone()) {
        int retryCount = 0;
        // 超时时长/10（FIXME:这个数值是未经过数据淬炼的，所以可能不是相对优的数值）
        long waitSleepMilliseconds = timeouts / 10;
        // 如果任务未完成，且超过重试次数，则进行休眠等待
        while (!future.isDone() && retryCount++ < POSTPONE_COUNT_MAX) {
          log.debug("lock '{}' is postpone of retry count {} in milliseconds {}", key, retryCount, waitSleepMilliseconds);
          TimeUnit.MICROSECONDS.sleep(waitSleepMilliseconds);
        }
      }

      // 如果经过了重试依然未处理完成，抛出异常（因为未考虑取消任务是否会造成未知的影响，暂且不取消任务）
      if (!future.isDone()) {
        throw new LockTimeoutException(String.format("lock '%s' execute is timeout", key));
      }

      try {
        // 开启了顺延时，在上面就已经延长过调用了，所以这里只接获取值即可，否则使用锁注解上的超时时间
        Object result = future.get(lock.autoPostpone() ? 1 : timeouts, TimeUnit.MILLISECONDS);

        log.info("lock '{}' execute result {}", key, result);

        // 如果异步结果为异常，则直接抛出原生异常
        if (result instanceof Throwable) {
          throw (Throwable) result;
        }

        return result;
      } catch (TimeoutException e) {
        // 如果异步调用超时，则抛出超时异常
        throw new LockTimeoutException(String.format("lock '%s' execute is timeout", key), e);
      }
    } finally {
      if (lockValue.isPresent()) {
        if (lockProvider.unlock(key, lockValue)) {
          log.info("lock '{}' released", key);
        } else {
          log.warn("lock '{}' release failure", key);
        }
      }
      log.info("lock '{}' end", key);
    }
  }

  /**
   * 锁Key解析器
   * 
   * @see https://conkeyn.iteye.com/blog/2354644
   * @see http://stackoverflow.com/questions/33024977/pass-method-argument-in-aspect-of-custom-annotation 
   * @author Administrator
   *
   * @param <T>
   */
  private class LockKeyExpressionEvaluator<T> extends CachedExpressionEvaluator {

    // shared param discoverer since it caches data internally
    private final ParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();

    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>(64);

    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    /** 
     * Create the suitable {@link EvaluationContext} for the specified event handling on the 
     * specified method. 
     */
    public EvaluationContext createEvaluationContext(Object object, Class<?> targetClass, Method method, Object[] args) {
      Method targetMethod = getTargetMethod(targetClass, method);
      ExpressionRootObject root = new ExpressionRootObject(object, args);
      return new MethodBasedEvaluationContext(root, targetMethod, args, this.paramNameDiscoverer);
    }

    /** 
     * Specify if the condition defined by the specified expression matches. 
     */
    public T condition(String conditionExpression, AnnotatedElementKey elementKey, EvaluationContext evalContext, Class<T> clazz) {
      return getExpression(this.conditionCache, elementKey, conditionExpression).getValue(evalContext, clazz);
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {
      AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
      Method targetMethod = this.targetMethodCache.get(methodKey);
      if (targetMethod == null) {
        targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        if (targetMethod == null) {
          targetMethod = method;
        }
        this.targetMethodCache.put(methodKey, targetMethod);
      }
      return targetMethod;
    }
  }

  private class ExpressionRootObject {

    private final Object object;

    private final Object[] args;

    public ExpressionRootObject(Object object, Object[] args) {
      this.object = object;
      this.args = args;
    }

    public Object getObject() {
      return object;
    }

    public Object[] getArgs() {
      return args;
    }
  }

}
