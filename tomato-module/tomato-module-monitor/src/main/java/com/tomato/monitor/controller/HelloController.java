package com.tomato.monitor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * @author lizhifu
 * @since 2023/5/2
 */
@RestController
@Slf4j
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		log.info("hello");
		return "hello";
	}

}
