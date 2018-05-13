package com.chinasoft.it.wecode.agent.trace;

import java.lang.instrument.Instrumentation;

/**
 * Instrumentation 
 * Instrumentation 
 * 
 * Java SE 6 的新特性：虚拟机启动后的动态 instrument
 * https://blog.csdn.net/zhyhang/article/details/17027441
 * @author Administrator
 *
 */
public class TraceApp {

	public static void premain(String agentOps, Instrumentation inst) {
		System.out.println("=========premain方法执行========");
		System.out.println(agentOps);
		// 添加Transformer
		//inst.addTransformer(new FirstAgent());
	}
}
