package com.tomato.sys.domain.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录
 *
 * @author lizhifu
 * @date 2022/12/12
 */
@Data
public class LoginReq {
    @NotBlank(message = "登录名不能为空")
    private String loginName;

    @NotBlank(message = "密码不能为空")
    private String loginPwd;

    @NotBlank(message = "验证码不能为空")
    private String captchaCode;

    @NotBlank(message = "验证码UUID不能为空")
    private String captchaUuid;
}
