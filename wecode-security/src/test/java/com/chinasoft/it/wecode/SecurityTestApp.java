package com.chinasoft.it.wecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SecurityTestApp {

	public static void main(String[] args) {
		SpringApplication.run(SecurityTestApp.class, args);
	}

}
