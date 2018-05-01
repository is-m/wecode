package com.chinasoft.it.wecode.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Observable;

/**
 * JDK 动态代理 被代理的类需要有接口才行，cglib可以无接口
 * 
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
