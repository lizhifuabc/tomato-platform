package com.tomato.auth.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@Controller
public class DefaultController {

	@GetMapping("/")
	public String root() {
		return "redirect:/index";
	}

	@GetMapping("/index")
	public String index() {
		return "index";
	}

}
