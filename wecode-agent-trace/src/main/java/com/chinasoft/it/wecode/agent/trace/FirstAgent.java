package com.chinasoft.it.wecode.agent.trace;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class FirstAgent {

	public final String injectedClassName = "com.xueyou.agentdemo.App";
	public final String methodName = "hello";

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		className = className.replace("/", ".");
		// System.out.println(className);
		if (className.equals(injectedClassName)) {
			CtClass ctclass = null;
			try {
				ctclass = ClassPool.getDefault().get(className);// 使用全称,用于取得字节码类<使用javassist>
				CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);// 得到这方法实例
				ctmethod.insertBefore("System.out.println(11111111);");
				return ctclass.toBytecode();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}
}
