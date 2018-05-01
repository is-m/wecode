package com.chinasoft.it.wecode.common.proxy;

/**
 * 代理类工厂
 * 
 * @author Administrator
 *
 */
public class InvocationFactory {

	/**
	 * 创建代理对象，并添加观察者，通常观察者建议使用 com.chinasoft.it.wecode.common.proxy
	 * 包中提供的Event作为具体的参数，当然自己实现也是没有问题的
	 * 
	 * 自己实现需要自己按自己的要求去解析MethodContext即可，可以参考Event实现内容
	 * 
	 * {@code 
	 * 	
	 *   * JDK 不支持没有接口的类实现代理
	 *   InputStream proxyInputStream = InvocationFactory.create(tempInputStream, new InvocationAfterEvent() {
     *      @Override
	 *      public void handle(MethodContext context) {
	 *          if ("close".equals(context.getMethod().getName())) {
	 *              logger.info("外部调用了代理流的关闭代码，准备移除自动关闭流的对象.");
	 *              autoCloseStreams.remove(context.getTarget());
	 *          }
	 *  	 }
	 *   }); 
	 * }
	 * 
	 * @param target
	 * @param obersers 观察者
	 * @return
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T create(Object target, AbstractEvent... events) {
		MyInvocationHandler invocationHandler = new MyInvocationHandler(target);
		if (events != null) {
			for (AbstractEvent event : events) {
				invocationHandler.addObserver(event);
			}
		}
		return (T) invocationHandler.getProxy();
	}
}
