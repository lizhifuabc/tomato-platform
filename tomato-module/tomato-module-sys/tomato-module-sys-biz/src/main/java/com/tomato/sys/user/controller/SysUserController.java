package com.tomato.sys.user.controller;

import com.tomato.common.resp.Resp;
import com.tomato.sys.domain.req.SysUserCreateReq;
import com.tomato.sys.user.service.SysUserService;
import com.tomato.web.common.BaseController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


/**
 * 系统用户相关
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@RestController
public class SysUserController extends BaseController {
    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 添加用户
     * @param sysUserCreateReq
     * @return
     */
    @PostMapping("/sys/user/create")
    public Resp createSysUser(@Valid @RequestBody SysUserCreateReq sysUserCreateReq) {
        return sysUserService.createSysUser(sysUserCreateReq);
    }
    /**
     * 更新禁用/启用状态
     * @param id id
     * @return 成功
     */
    @GetMapping("/sys/user/update/disabled/{id}")
    public Resp updateDisableFlag(@PathVariable Long id) {
        return Resp.buildSuccess();
    }
}
