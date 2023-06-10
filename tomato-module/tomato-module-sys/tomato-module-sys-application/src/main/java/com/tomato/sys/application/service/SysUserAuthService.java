package com.tomato.sys.application.service;

import com.tomato.sys.application.dto.SysLoginDTO;
import com.tomato.sys.application.resp.SysLoginResp;

/**
 * 登录
 *
 * @author lizhifu
 * @since 2023/4/22
 */
public interface SysUserAuthService {
    /**
     * 登录
     *
     * @param sysLoginDTO 登录
     * @return SysLoginResp 登录返回
     */
    SysLoginResp login(SysLoginDTO sysLoginDTO);

    /**
     * 刷新token
     *
     * @param refreshToken refreshToken
     * @return SysLoginResp 刷新token返回
     */
    SysLoginResp refreshToken(String refreshToken);
}
