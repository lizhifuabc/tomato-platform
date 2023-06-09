package com.tomato.sys.controller;

import com.tomato.sys.application.adapter.SysLoginAdapter;
import com.tomato.sys.application.resp.SysLoginResp;
import com.tomato.sys.application.service.SysUserLoginService;
import com.tomato.common.resp.Resp;
import com.tomato.sys.application.req.SysLoginReq;
import com.tomato.sys.domain.constants.RequestHeaderConstant;
import com.tomato.web.core.common.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 登录
 *
 * @author lizhifu
 * @since  2022/12/12
 */
@RestController
@Tag(name = "登录相关", description = "登录相关")
@Slf4j
public class SysUserLoginController extends BaseController {
    private final SysUserLoginService sysUserLoginService;

    public SysUserLoginController(SysUserLoginService sysUserLoginService) {
        this.sysUserLoginService = sysUserLoginService;
    }

    @PostMapping("/sys/user/login")
    @Operation(summary = "登录", description = "登录")
    public Resp<SysLoginResp> login(@Valid @RequestBody SysLoginReq sysLoginReq) {
        log.info("登录:{}",sysLoginReq);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysLoginResp login = sysUserLoginService.login(SysLoginAdapter.convert(sysLoginReq));
        return Resp.of(login);
    }
    @GetMapping("/sys/user/logout")
    public Resp<Void> logout(@RequestHeader(value = RequestHeaderConstant.TOKEN, required = false) String token) {
        return Resp.buildSuccess();
    }
}
