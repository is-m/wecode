package com.chinasoft.it.wecode.security.authentication.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinasoft.it.wecode.common.util.WebUtils;
import com.chinasoft.it.wecode.security.authentication.service.LoginService;

@Controller
public class LoginApi {

	private LoginService loginService;

	@RequestMapping("/login")
	public String login(HttpServletRequest req, HttpServletResponse resp) {
		String indexPage = "/web/index.html"; 
		
		return "redirect:"+indexPage;
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest req, HttpServletResponse resp) {  
		//  TODO 这个配置可能是通过字典配置的，也可能是当前系统的登录页面
		String loginPage = "/web/page/login.html"; 
		return "redirect:"+loginPage;
	}
}
