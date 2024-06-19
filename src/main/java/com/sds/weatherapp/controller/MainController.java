package com.sds.weatherapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String getMain() {
		
		return "/main/index9";
	}
	
	
	
	@GetMapping("/test")
	public String getTest() {
		return "/main/test";
	}
}