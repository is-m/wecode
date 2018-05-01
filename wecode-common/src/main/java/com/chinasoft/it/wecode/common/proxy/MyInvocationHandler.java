package com.chinasoft.it.wecode.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Observable;

/**
 * JDK 动态代理 被代理的类需要有接口才行，cglib可以无接口
 * 
 * 目前关于自定义的代理和Spring的代理的区别就是Spring代理的内容必须是Spring容器管理的内容，如果自己new的对象或者其他方式获取的对象
 * 不在Spring容器中则无法利用到Spring的代理动作，并且自己的可能拥有更多的灵活性
 * 
 * 还有SPring的AOP 可以有两种实现方式JDK和CGLIB，其区别是JDK只能构造拥有接口的代理对象，
 * 而CGLIB可以构造无接口的对象的代理（动态Code）
 * @author Administrator
 *         关于代理模式：https://blog.csdn.net/u013851082/article/details/71739434
 *         关于观察者模式：https://www.cnblogs.com/ncyhl/p/8017830.html
 */
public class MyInvocationHandler extends Observable implements InvocationHandler {

	private Object target = null;

	/**
	 * 
	 * @param waifForProxyObject
	 *            需要代理的对象
	 */
	public MyInvocationHandler(Object waifForProxyObject) {
		this.target = waifForProxyObject;
	}

	public Object getProxy() {
		// super class is need get interfaces ?
		Class<?>[] interfaces = target.getClass().getInterfaces();
		if(interfaces == null || interfaces.length == 0) {
			throw new IllegalArgumentException("no found interface for proxy " + target.getClass() + ", you can use cglib created proxy object?");
		}
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(),
				this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;

		this.notifyObservers(new MethodContext(target, method, ActionType.before, args));

		try {
			result = method.invoke(proxy, args);
			this.notifyObservers(new MethodContext(target, method, ActionType.after, args));
		} catch (Exception e) {
			// no set exception
			this.notifyObservers(new MethodContext(target, method, ActionType.exception, args));
			throw e;
		}
		// beforeReturn
		return result;
	}

	public static enum ActionType {
		before, after, exception
	}

	public static class MethodContext {

		/**
		 * 被代理者
		 */
		private Object target;

		private Method method;

		private ActionType actionType;

		private Object[] args;

		public Object getTarget() {
			return target;
		}

		public void setTarget(Object target) {
			this.target = target;
		}

		public Method getMethod() {
			return method;
		}

		public void setMethod(Method method) {
			this.method = method;
		}

		public Object[] getArgs() {
			return args;
		}

		public void setArgs(Object[] args) {
			this.args = args;
		}

		public ActionType getActionType() {
			return actionType;
		}

		public void setActionType(ActionType actionType) {
			this.actionType = actionType;
		}

		public MethodContext(Object target, Method method, ActionType actionType, Object[] args) {
			this.method = method;
			this.actionType = actionType;
			this.args = args;
		}
	}

}
