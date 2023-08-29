package com.tomato.security.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.security.config.SecurityProperties;
import com.tomato.security.handler.CustomAuthenticationHandler;
import com.tomato.security.handler.CustomRememberMeServices;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义登录过滤器
 *
 * @author lizhifu
 * @since 2023/4/23
 */
@Component
@Slf4j
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

	@Resource
	private ObjectMapper objectMapper;

	public CustomLoginFilter(AuthenticationManager authenticationManager,
			CustomAuthenticationHandler authenticationHandler, CustomRememberMeServices rememberMeServices,
			SecurityProperties securityProperties) {
		super(authenticationManager);
		// 失败时的处理方法
		setAuthenticationFailureHandler(authenticationHandler);
		// 登陆成功的处理方法
		setAuthenticationSuccessHandler(authenticationHandler);
		// rememberMe
		setRememberMeServices(rememberMeServices);
		// 登陆使用的路径
		setFilterProcessesUrl(securityProperties.getLogin());
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		String username = null;
		String password = null;
		String verifyCode = null;
		String rememberMe = null;
		// 如果Content-Type是Json，则从Body中获取请求参数，否则从Form表单中获取
		if (jsonType(request)) {
			try {
				Map<String, String> map = objectMapper.readValue(request.getInputStream(), new TypeReference<>() {
				});
				username = map.get(getUsernameParameter());
				password = map.get(getPasswordParameter());
			}
			catch (IOException e) {
				log.error("jsonType error", e);
			}
		}
		else {
			username = obtainUsername(request);
			password = obtainPassword(request);
		}
		username = (username != null) ? username.trim() : "";
		password = (password != null) ? password : "";
		UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
				password);
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private static boolean jsonType(HttpServletRequest request) {
		final String contentType = request.getContentType();
		return contentType.contains("application/json");
	}

}