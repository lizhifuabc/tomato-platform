package com.tomato.sys.controller;

import com.tomato.common.exception.BusinessException;
import com.tomato.sys.application.req.SysUserUpdatePasswordReq;
import com.tomato.sys.application.service.SysUserPasswordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关
 *
 * @author lizhifu
 * @since 2023/6/8
 */
@RestController
@Tag(name = "用户相关", description = "用户相关 API")
public class SysUserManagerController {

	private final SysUserPasswordService sysUserPasswordService;

	public SysUserManagerController(SysUserPasswordService sysUserPasswordService) {
		this.sysUserPasswordService = sysUserPasswordService;
	}

	@PostMapping("/sys/user/add")
	@PreAuthorize("hasAuthority('sys:user:add')")
	public void add() {

	}

	@PostMapping("/sys/user/update")
	@PreAuthorize("hasAuthority('sys:user:update')")
	public void update() {

	}

	@PostMapping("/sys/user/updatePassword")
	@PreAuthorize("hasAuthority('sys:user:updatePassword')")
	public void updatePassword(@Valid @RequestBody SysUserUpdatePasswordReq sysUserUpdatePasswordReq) {
		// 新旧密码不能相同
		if (sysUserUpdatePasswordReq.getOldPassword().equals(sysUserUpdatePasswordReq.getNewPassword())) {
			throw new BusinessException("新旧密码不能相同");
		}
		sysUserPasswordService.updatePassword(sysUserUpdatePasswordReq);
	}

}
