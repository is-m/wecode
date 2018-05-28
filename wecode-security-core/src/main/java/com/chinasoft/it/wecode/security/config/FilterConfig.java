package com.chinasoft.it.wecode.security.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chinasoft.it.wecode.security.servlet.UserContextFilter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean myOncePerRequestFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new UserContextFilter());
		registration.addUrlPatterns("/*");
		registration.setName("UserContextFilter");
		registration.setOrder(2);
		return registration;
	}

}
