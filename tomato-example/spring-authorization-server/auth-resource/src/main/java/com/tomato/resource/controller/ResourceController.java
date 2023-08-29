package com.tomato.resource.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源服务器
 *
 * @author lizhifu
 * @since 2023/4/6
 */
@RestController
public class ResourceController {

	@GetMapping("/hello")
	public HttpEntity<String> hello() {
		return ResponseEntity.ok("auth-resource -> hello");
	}

}
