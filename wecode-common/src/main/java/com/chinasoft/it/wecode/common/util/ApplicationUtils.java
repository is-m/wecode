package com.chinasoft.it.wecode.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class ApplicationUtils implements ApplicationContextAware {

	private static ApplicationContext ac;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationUtils.ac = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return ac;
	}

	public static <T> T getBean(Class<T> clz) {
		return ac.getBean(clz);
	}

	public static <T> T getBean(Class<T> clz, String name) {
		return ac.getBean(clz, name);
	}

}
