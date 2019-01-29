package com.gx.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping(value = {"/", "/home"})
	public String home() {
		return "home";
	}

	@GetMapping("/user")
	public String user() {
		return "user";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	@GetMapping("/403")
	public String Error403() {
		return "403";
	}

}
