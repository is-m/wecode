package com.chinasoft.it.wecode.common.proxy;

import com.chinasoft.it.wecode.common.proxy.MyInvocationHandler.ActionType;
import com.chinasoft.it.wecode.common.proxy.MyInvocationHandler.MethodContext;

/**
 * 这个就是传说中的模版方法模式 外部写法难道
 * 
 * @author Administrator
 *
 */
public abstract class InvocationAfterEvent extends AbstractEvent {

	@Override
	public boolean doThis(MethodContext context) {
		return ActionType.after.equals(context.getActionType());
	}
}
