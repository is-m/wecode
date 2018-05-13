package com.chinasoft.it.wecode.trace.spi.service.impl;

import com.chinasoft.it.wecode.trace.spi.service.TraceService;
import com.chinasoft.it.wecode.trace.utils.CallStack;

/**
 * 链路追踪服务
 * 
 * @author Administrator
 *
 */
public class ConsoleTraceService implements TraceService {

	@Override
	public void push(CallStack callStack) {
		System.out.println(callStack);
	}

}
