package com.chinasoft.it.wecode.sign.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "redirect:/web/index.html";
	}
	
	@GetMapping("/pages")
	public String pages() {
		return "redirect:/dist/index.html";
	}
 
	/**
     * 访问页面时默认访问当前目录的URLindex.html
     * TODO:待实现，下面的实现不知道为什么所有进去都进来，而我实际只想拦截   /  作为结束的请求
     * https://blog.csdn.net/adsadadaddadasda/article/details/78869083
     * @param request
     * @return
    */
	//@GetMapping("/**/")
    //public String indexPage(HttpServletRequest request) {
	//  return "redirect:"+ request.getRequestURI().substring(request.getContextPath().length())+"/index.html";
    //}
   
	/*@GetMapping("/")
	public String homePage() {
		return "redirect:/index.html";
	}*/
}
