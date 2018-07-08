package com.chinasoft.it.wecode.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK 接口代理，用于临时实现只有接口没有实现类的场景，例如Mybatis的Mapper，或者是没有实现类的JpaRepository等类似场景，
 * 但是如果要做到能被Spring注入，还需要将生成的代理类注入到SpringContext
 * 
 * @author Administrator
 *
 */
public class JDKInterfaceInvocationHandler implements InvocationHandler {

	/**
	 * 代理接口
	 */
	private Class<?>[] interfaces;

	/**
	 * 方法处理器
	 */
	private MethodProcesser processer;

	/**
	 * 被代理对象
	 */
	private Object target;

	public JDKInterfaceInvocationHandler(MethodProcesser processer, Class<?>... interfaces) {
		this(new Object(), processer, interfaces);
	}

	public JDKInterfaceInvocationHandler(Object waitForProxy, MethodProcesser processer, Class<?>... interfaces) {
		this.target = waitForProxy;
		this.processer = processer;
		this.interfaces = interfaces;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			Object invork = processer.invork(target, method, args);

			return invork;
		} catch (Exception e) {
		}
		return null;
	}

	public Object getProxy() {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, this);
	}

	@FunctionalInterface
	public interface MethodProcesser {

		Object invork(Object target, Method method, Object[] args) throws Exception;

	}
}
