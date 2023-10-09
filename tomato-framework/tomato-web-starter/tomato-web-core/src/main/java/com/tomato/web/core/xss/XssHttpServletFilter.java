package com.tomato.web.core.xss;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Xss 过滤器
 *
 * @author lizhifu
 * @since 2023/10/9
 */
@Slf4j
public class XssHttpServletFilter implements Filter {
	private final XssUtil xssUtil;

	public XssHttpServletFilter(XssUtil xssUtil) {
		this.xssUtil = xssUtil;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request,xssUtil);
		filterChain.doFilter(xssRequest, servletResponse);
	}
}
