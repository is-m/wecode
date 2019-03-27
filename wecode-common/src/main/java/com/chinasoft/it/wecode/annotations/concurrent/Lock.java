package com.chinasoft.it.wecode.annotations.concurrent;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.chinasoft.it.wecode.common.lock.provider.LockProvider;

/**
 * 锁注解
 * 
 * 使用锁注解时可能会存在的异常
 * 1.方法调用异常，方法本身调用失败了(Throwable)
 * 2.锁访问异常，锁为乐观锁，且标记了乐观锁异常抛出时（LockAccessException）
 * 3.锁占用超时异常，通常是方法超过了锁允许的最大重试次数，例如： 悲观锁获取锁超时/业务方法执行超时 (LockTimeoutException)
 * 
 * 锁的应用场景：
 * 分布式部署（）
 * 1.秒杀时的订单扣减必须是线程安全的，但是原子类又是单机特性，所以适用分布式锁来扣减
 * 2.分布式定时任务，未使用第三方框架和平台的情况下，需要保证仅单机运行时适用。
 * 
 * 单机部署
 * 如果需要线程同步时也建议使用锁注解而不是java本身的同步关键字，因为，这个锁本身就是对同步的一个稍高一点的抽象
 * 
 * 注意：上面加了锁的方法，如果类不是被spring 管理的，或者方法未被LockAnnotationProcesser 类代理，可能就会失效（不排除以后会用其他方式来实现锁的注入）
 * 
 * 
 * @author Administrator
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Lock {

  /**
   * 锁Key，因为锁默认是加载在方法上面的，所以在未填充内容或者内容为default时，默认为包名.类名::方法名，也可以是用户自己指定
   * 或者可以使用Spring El表达式，例如 在方法是标注取第一个参数，第二个参数的内容 
   * {@code 
   *    @Lock(value="lock_#1")
   *    public void testLock(Map<String,Object> obj,long id)
   * }
   * 
   * @see https://stackoverflow.com/questions/33024977/pass-method-argument-in-aspect-of-custom-annotation
   * @return
   */
  String value() default "";

  /**
   * 是否分布式(默认是true)
   * 是(true)：分布式锁
   * 否(false)：多线程锁
   * FIXME:这个最终需要根据应用类型来确定，如果是单体应用，例如单机部署的则应该为false,只有分布式部署时才需要考虑是否为true
   * @return
   */
  boolean distributed() default true;

  /**
   * 是否乐观的
   * 是(true):乐观锁，表示当有人占用时自动跳过锁不进行锁住的内容的处理
   * 否(false):悲观锁，表示有人占用同一锁资源时，一直等待
   * @return
   */
  boolean optimistic() default true;

  /**
   * 乐观锁时是静默不执行锁住的函数，还是抛出异常（只有在optimistic = true 时生效），为true时，会抛出 LockAccessException
   * @return
   */
  boolean optimisticThrowable() default false;

  /**
   * 锁超时，锁在未显示释放的情况下，默认超时时间（防止因为应用本身的原因[终止，重启，异常 等]导致的锁未能释放的补偿措施）
   * @return
   */
  long timeoutInMilliseconds() default LockProvider.DEFAULT_TIMEOUT_IN_MILLISECONDS;

  /**
   * 是否自动顺延
   * 是(true): 当指定为自动顺延时，方法在调用的过程中会自动延长锁的生命周期,注意：这里会将方法转换成异步方法，如果涉及线程变量取值的需要先进行同步处理
   * 否(false):不顺延，认为是在超时时间完成一定可以完成任务
   * @return
   */
  boolean autoPostpone() default true;

  /**
   * 线程上下文设置程序类参数（只能为 Void.class 或者 LockContextProvider 的实现类）
   * 备注：因为最终被锁的方法是异步执行的，所以如果方法中包含了使用线程变量的内容（例如请求，用户，等上下文信息），
   *      则需要实现了LockContextProvider接口的类实现线程变量的内容的拷贝，初始化和销毁
   * @return
   */
  Class<?> contextProvider() default Void.class;
  
}
