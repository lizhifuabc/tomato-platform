package com.tomato.sys.login.domain.req;

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
    private String password;
}
