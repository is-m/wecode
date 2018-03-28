package com.chinasoft.it.wecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// https://blog.csdn.net/u011116672/article/details/77823867 retry
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableJpaRepositories
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
