package com.chinasoft.it.wecode.agent.trace.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.regex.Pattern;

import com.chinasoft.it.wecode.agent.trace.config.CallStackConfig;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * 调用链路信息注入
 * 
 * @author Administrator
 *
 */
public class CallStackTransformer implements ClassFileTransformer {

	private Pattern pattern = Pattern.compile("^com");

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		CallStackConfig config = CallStackConfig.getInstance();

		className = className.replace("/", ".");

		if (className.indexOf("com.") == 0) {
			System.out.println("Class " + className + ",检查通过 准备注入");
			try {
				CtClass clz = ClassPool.getDefault().get(className);// 使用全称,用于取得字节码类<使用javassist>

				// ? 这个才是方法集合 clz.getDeclaredBehaviors()
				CtMethod[] declaredMethods = clz.getDeclaredMethods();
				for (CtMethod method : declaredMethods) {
					method.insertBefore(config.getInjectMethodBefore());
					method.insertAfter(config.getInjectMethodAfter());
					System.out.println();
				}

				return clz.toBytecode();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return classfileBuffer;
	}

}
