package com.chinasoft.it.wecode.agent.trace;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * JDK 1.6 代码追踪 用于应用 启动后的JAVA调用链路监控 包含类，方法，入参，返回值
 * 
 * @author Administrator
 *
 */
public class TraceAgentmain {

	public static void agentmain(String agentArgs, Instrumentation inst)
			throws ClassNotFoundException, UnmodifiableClassException, InterruptedException {
		System.out.println("Agent Main Done");
	/*	inst.addTransformer(new Transformer(), true);
		inst.retransformClasses(TransClass.class);*/
	}
}
