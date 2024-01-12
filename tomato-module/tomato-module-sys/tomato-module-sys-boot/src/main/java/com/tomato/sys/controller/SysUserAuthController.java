package com.tomato.sys.controller;

import com.tomato.sys.application.adapter.SysLoginAdapter;
import com.tomato.sys.application.resp.SysLoginResp;
import com.tomato.sys.application.service.SysUserAuthService;
import com.tomato.common.domain.resp.Resp;
import com.tomato.sys.application.req.SysLoginReq;
import com.tomato.sys.domain.constants.RequestHeaderConstant;
import com.tomato.web.core.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 系统用户授权
 *
 * @author lizhifu
 * @since 2022/12/12
 */
@RestController
@Tag(name = "系统用户授权", description = "系统用户授权")
@Slf4j
public class SysUserAuthController extends BaseController {

	private final SysUserAuthService sysUserAuthService;

	public SysUserAuthController(SysUserAuthService sysUserAuthService) {
		this.sysUserAuthService = sysUserAuthService;
	}

	@PostMapping("/sys/user/auth/login")
	@Operation(summary = "登录", description = "登录")
	public Resp<SysLoginResp> login(@Valid @RequestBody SysLoginReq sysLoginReq) {
		log.info("登录:{}", sysLoginReq);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
			.getRequest();
		SysLoginResp login = sysUserAuthService.login(SysLoginAdapter.convert(sysLoginReq));
		return Resp.of(login);
	}

	@GetMapping("/sys/user/auth/logout")
	public Resp<Void> logout(@RequestHeader(value = RequestHeaderConstant.TOKEN, required = false) String token) {
		return Resp.buildSuccess();
	}

	@PostMapping("/sys/user/auth/refresh-token")
	public Resp<SysLoginResp> refreshToken(HttpServletRequest request, HttpServletRequest response) {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith(RequestHeaderConstant.AUTHORIZATION_BEARER)) {
			Resp.buildFailure("header token 不存在");
		}
		assert authHeader != null;
		SysLoginResp sysLoginResp = sysUserAuthService.refreshToken(authHeader.substring(7));
		return Resp.of(sysLoginResp);
	}

}
