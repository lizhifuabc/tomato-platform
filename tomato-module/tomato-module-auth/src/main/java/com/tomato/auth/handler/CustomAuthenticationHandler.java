package com.tomato.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.common.resp.Resp;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

/**
 * 自定义认证处理器
 * <p>
 * 1.设置Content-Type为application/json;charset=UTF-8
 * <p>
 * 2.根据情况设置状态码
 * <p>
 * 3.将返回结果写入到response
 * <p>
 * AuthenticationSuccessHandler:认证成功处理器<br>
 * AuthenticationFailureHandler:认证失败处理器<br>
 * LogoutSuccessHandler:登出成功处理器<br>
 * SessionInformationExpiredStrategy:session过期处理器<br>
 * AccessDeniedHandler:权限不够处理器（访问拒绝处理器）->认证通过，访问没有权限的 URI<br>
 * AuthenticationEntryPoint:认证失败处理类（未认证处理）->未登录访问，前端重定向到登录页面<br>
 * </p>
 *
 * @author lizhifu
 * @since 2023/4/23
 */
@Slf4j
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler,
		LogoutSuccessHandler, SessionInformationExpiredStrategy, AccessDeniedHandler, AuthenticationEntryPoint {

	private final static String APPLICATION_JSON_UTF8_VALUE = MediaType.APPLICATION_JSON_UTF8_VALUE;

	@Resource
	private ObjectMapper objectMapper;

	/**
	 * 认证失败处理
	 * @param request that resulted in an <code>AuthenticationException</code>
	 * @param response so that the user agent can begin authentication
	 * @param authException that caused the invocation
	 * @throws IOException 异常
	 * @throws ServletException 异常
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.info("[commence][访问 URL({}) 时，没有登录({})]", request.getRequestURI(), authException.getLocalizedMessage());
		String detailMessage = authException.getClass().getSimpleName() + " " + authException.getLocalizedMessage();
		if (authException instanceof InsufficientAuthenticationException) {
			detailMessage = "请登陆后再访问";
		}
		// TODO 这里使用 deprecated, 官方说浏览器已经默认支持utf8了
		response.setContentType(APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter()
			.println(objectMapper.writeValueAsString(Resp.buildFailure(HttpStatus.UNAUTHORIZED.toString(), "认证异常")));
	}

	/**
	 * 权限不足时的处理:请求被禁止、超出访问权限。与401不同，请求已经通过了身份验证，只是没有获得资源授权
	 * @param request that resulted in an <code>AccessDeniedException</code>
	 * @param response so that the user agent can be advised of the failure
	 * @param accessDeniedException that caused the invocation
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		String detailMessage = null;
		if (accessDeniedException instanceof MissingCsrfTokenException) {
			detailMessage = "缺少CSRF TOKEN,请从表单或HEADER传入";
		}
		else if (accessDeniedException instanceof InvalidCsrfTokenException) {
			detailMessage = "无效的CSRF TOKEN";
		}
		else if (accessDeniedException instanceof CsrfException) {
			detailMessage = accessDeniedException.getLocalizedMessage();
		}
		else if (accessDeniedException instanceof AuthorizationServiceException) {
			detailMessage = AuthorizationServiceException.class.getSimpleName() + " "
					+ accessDeniedException.getLocalizedMessage();
		}
		// 打印 warn 的原因是，不定期合并 warn，看看有没恶意破坏 TODO
		// log.warn("[handle][访问 URL({}) 时，用户({}) 权限不够:{}]", request.getRequestURI(),
		// SecurityFrameworkUtil.getLoginUser(), detailMessage);
		response.setContentType(APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.getWriter()
			.println(objectMapper.writeValueAsString(Resp.buildFailure(HttpStatus.FORBIDDEN.toString(), "禁止访问")));
	}

	/**
	 * 认证失败时的处理
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("[onAuthenticationFailure][认证失败({})]", exception.getLocalizedMessage());
		response.setContentType(APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter()
			.println(objectMapper.writeValueAsString(Resp.buildFailure(HttpStatus.UNAUTHORIZED.toString(), "登陆失败")));
	}

	/**
	 * 认证成功时的处理,登陆成功
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setContentType(APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpStatus.OK.value());
		// SecurityContext在设置Authentication的时候并不会自动写入Session，读的时候却会根据Session判断，所以需要手动写入一次，否则下一次刷新时SecurityContext是新创建的实例。
		// https://yangruoyu.blog.csdn.net/article/details/128276473
		request.getSession()
			.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
					SecurityContextHolder.getContext());
		response.getWriter().println(objectMapper.writeValueAsString(Resp.of(authentication)));
		// 清理使用过的验证码 TODO
		// request.getSession().removeAttribute(VERIFY_CODE_KEY);
	}

	/**
	 * 登出成功处理:注销成功
	 * @param request 请求
	 * @param response 响应
	 * @param authentication 认证信息
	 * @throws IOException 异常
	 * @throws ServletException 异常
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		response.setContentType(APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpStatus.OK.value());
		response.getWriter().println(objectMapper.writeValueAsString(Resp.of(authentication)));
	}

	/**
	 * 会话过期处理
	 * @throws IOException 异常
	 * @throws ServletException 异常
	 */
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		String message = "该账号已从其他设备登陆,如果不是您自己的操作请及时修改密码";
		final HttpServletResponse response = event.getResponse();
		response.setContentType(APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		String detailMessage = event.getSessionInformation().toString();
		response.getWriter()
			.println(objectMapper.writeValueAsString(Resp.buildFailure(HttpStatus.UNAUTHORIZED.toString(), message)));
	}

}
