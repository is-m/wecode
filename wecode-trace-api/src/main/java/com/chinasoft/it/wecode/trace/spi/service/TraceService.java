package com.chinasoft.it.wecode.trace.spi.service;

import com.chinasoft.it.wecode.trace.utils.CallStack;

public interface TraceService {

	/**
	 * 推送callStack
	 * 
	 * @param callStack
	 */
	void push(CallStack callStack);

}
