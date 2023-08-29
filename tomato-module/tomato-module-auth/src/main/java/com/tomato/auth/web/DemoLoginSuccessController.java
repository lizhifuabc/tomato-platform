package com.tomato.auth.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 默认登录成功页
 *
 * @author lizhifu
 * @since 2023/5/3
 */
@Controller
@Slf4j
public class DemoLoginSuccessController {

	@PostMapping("/login/success")
	public String login(HttpServletRequest request) {
		log.info("进入login success 页面");
		return "login";
	}

}
