package com.chinasoft.it.wecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableCaching
@EnableJpaRepositories
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class SignApp {

	public static void main(String[] args) {
		SpringApplication.run(SignApp.class, args);
	}
	
}
