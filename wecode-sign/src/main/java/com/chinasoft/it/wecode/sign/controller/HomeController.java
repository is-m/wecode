package com.chinasoft.it.wecode.sign.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
public class HomeController {

	@GetMapping("/")
	public String homePage() {
		return "redirect:/dist/index.html";
	}
}
