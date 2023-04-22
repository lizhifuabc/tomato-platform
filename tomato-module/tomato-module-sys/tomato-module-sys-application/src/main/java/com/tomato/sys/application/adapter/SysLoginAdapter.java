package com.tomato.sys.application.adapter;

import com.tomato.sys.application.dto.SysLoginDTO;
import com.tomato.sys.application.req.SysLoginReq;

/**
 * 请求入参转换器
 *
 * @author lizhifu
 * @since 2023/4/22
 */
public final class SysLoginAdapter {
    private SysLoginAdapter() {
    }
    /**
     * 转换
     * @param sysLoginReq 请求入参
     * @return SysLoginDTO
     */
    public static SysLoginDTO convert(SysLoginReq sysLoginReq) {
        SysLoginDTO sysLoginDTO = new SysLoginDTO();
        sysLoginDTO.setUsername(sysLoginReq.getUsername());
        sysLoginDTO.setPassword(sysLoginReq.getPassword());
        sysLoginDTO.setCaptchaCode(sysLoginReq.getCaptchaCode());
        sysLoginDTO.setCaptchaUuid(sysLoginReq.getCaptchaUuid());
        return sysLoginDTO;
    }
}
