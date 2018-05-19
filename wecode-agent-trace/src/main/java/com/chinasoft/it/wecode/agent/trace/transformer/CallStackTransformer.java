package com.chinasoft.it.wecode.agent.trace.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.regex.Pattern;

import com.chinasoft.it.wecode.agent.trace.config.CallStackConfig;
import com.chinasoft.it.wecode.agent.trace.utils.Javassists;
import com.chinasoft.it.wecode.trace.spi.annotation.Trace;

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
					// TODO 可以根据配置模式来确定开始的方法，例如所有controller api 都可能是开始方法，
					// 又或是所有的服务类的public方法是开始方法，但是只要当前线程已经开始了，则表示哪怕定义的开始方法也不行
					// 或者使用注解
					boolean hasAnnotation = method.hasAnnotation(Trace.class);
					if(hasAnnotation) {		
						Trace annotation = (Trace)method.getAnnotation(Trace.class); 
						method.insertBefore(config.getInjectMethodBefore());
					}
					Javassists.getMethodParamNames(method);
					method.insertBefore(config.getInjectMethodBefore());
					
					method.insertAfter(config.getInjectMethodAfter()); 
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
