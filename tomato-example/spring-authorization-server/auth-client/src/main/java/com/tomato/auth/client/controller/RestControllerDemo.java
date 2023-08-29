package com.tomato.auth.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestControllerDemo
 *
 * @author lizhifu
 * @since 2023/4/11
 */
@RestController
public class RestControllerDemo {

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

}
