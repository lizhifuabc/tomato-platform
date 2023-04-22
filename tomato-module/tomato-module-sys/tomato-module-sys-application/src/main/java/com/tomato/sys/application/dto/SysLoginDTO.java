package com.tomato.sys.application.dto;

import lombok.Data;

/**
 * 登录
 *
 * @author lizhifu
 * @since 2023/4/22
 */
@Data
public class SysLoginDTO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String captchaCode;
    /**
     * 验证码key
     */
    private String captchaUuid;
}
