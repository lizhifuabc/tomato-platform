package com.tomato.demo.controller.controller.user;

import com.tomato.demo.client.user.service.UserClientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/4/2
 */
@RestController
@RequestMapping("/player")
public class UserController {

	private final UserClientService userClientService;

	public UserController(UserClientService userClientService) {
		this.userClientService = userClientService;
	}

}
