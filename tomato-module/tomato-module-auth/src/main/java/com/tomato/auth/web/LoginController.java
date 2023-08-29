package com.tomato.auth.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录
 * <p>
 * {@link DefaultLoginPageConfigurer 默认登录页配置} {@link DefaultLoginPageGeneratingFilter
 * 默认登录页生成过滤器} {@link DefaultLogoutPageGeneratingFilter 默认登出页生成过滤器}
 *
 * @author lizhifu
 * @since 2023/5/3
 */
@Controller
@Slf4j
public class LoginController {

	@GetMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		log.info("进入login页面");
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}

}
