package com.chinasoft.it.wecode.common.proxy;

import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;

import com.chinasoft.it.wecode.common.proxy.MyInvocationHandler.MethodContext;
import com.chinasoft.it.wecode.common.util.LogUtils;

public abstract class AbstractEvent implements Observer {

	private static final Logger logger = LogUtils.getLogger();

	@Override
	public void update(Observable o, Object arg) {
		if (!(arg instanceof MethodContext)) {
			logger.warn("该类的实例只能用于com.chinasoft.it.wecode.common.proxy.MyInvocationHandle调用");
			return;
		}
		MethodContext context = (MethodContext) arg;
		if (doThis(context)) {
			handle(context);
		}
	}

	public abstract boolean doThis(MethodContext context);

	public abstract void handle(MethodContext context);

}
