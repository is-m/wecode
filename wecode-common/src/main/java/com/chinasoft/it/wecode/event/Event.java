package com.chinasoft.it.wecode.event;

/**
 * 事件
 * 
 * 
 * @author Administrator
 *
 * @param <T>
 */
public interface Event<T extends EventArg> {

	void action(Object sender, T arg);
	
}
