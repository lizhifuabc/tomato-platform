package com.tomato.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@RestController
public class MessagesController {

	@GetMapping("/messages")
	public String[] getMessages() {
		return new String[] { "Message 1", "Message 2", "Message 3" };
	}

}
