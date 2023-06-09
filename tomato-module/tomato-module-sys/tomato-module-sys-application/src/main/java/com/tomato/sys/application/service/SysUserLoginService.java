package com.tomato.sys.application.service;

import com.tomato.sys.application.dto.SysLoginDTO;
import com.tomato.sys.application.resp.SysLoginResp;

/**
 * 登录
 *
 * @author lizhifu
 * @since 2023/4/22
 */
public interface SysUserLoginService {
    /**
     * 登录
     *
     * @param sysLoginDTO 登录
     * @return SysLoginResp 登录返回
     */
    public SysLoginResp login(SysLoginDTO sysLoginDTO);
}
