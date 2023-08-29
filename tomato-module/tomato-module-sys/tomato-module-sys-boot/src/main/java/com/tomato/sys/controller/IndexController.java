package com.tomato.sys.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController
 *
 * @author lizhifu
 * @since 2023/6/9
 */
@RestController
@RequestMapping
public class IndexController {

	@RequestMapping("/")
	public String index() {
		return "Hello World";
	}

}
