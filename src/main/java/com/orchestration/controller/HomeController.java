package com.orchestration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	public static String variedText = "0";
	
	@GetMapping("/")
	public String home() {
		return "index";
	}

}
