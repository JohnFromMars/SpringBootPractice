package com.marsbase.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
	
	@RequestMapping("/admin")
	public String addmin(){
		return "admin";
	}

}
