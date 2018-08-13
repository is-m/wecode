package com.chinasoft.it.wecode.sign.controller;

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
	
	/*@GetMapping("/")
	public String homePage() {
		return "redirect:/index.html";
	}*/
}
