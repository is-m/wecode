package com.chinasoft.it.wecode.agent.trace;

import java.lang.instrument.Instrumentation;

import com.chinasoft.it.wecode.agent.trace.transformer.CallStackTransformer;

/**
 * JDK 1.5 代码追踪 (用于在main方法执行前注入代码实现方法级别的AOP功能)
 * 
 * @author Administrator
 *
 */
public class TracePremain {

	public static void premain(String agentOps, Instrumentation inst) {
		System.out.println("正在加载 Trace模块 args:" + agentOps);
		// 添加Transformer
		inst.addTransformer(new CallStackTransformer());
	}
}
