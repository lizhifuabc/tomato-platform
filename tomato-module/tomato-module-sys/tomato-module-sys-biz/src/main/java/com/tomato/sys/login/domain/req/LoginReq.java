package com.tomato.sys.login.domain.req;

import com.tomato.validator.validator.VerificationPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 登录
 *
 * @author lizhifu
 * @date 2022/12/12
 */
@Data
public class LoginReq {
    @NotBlank(message = "登录名不能为空")
    @Length(max = 30, message = "登录账号最多30字符")
    private String loginName;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = VerificationPattern.PWD_REGEXP, message = "请输入6-15位密码(数字|大小写字母|小数点)")
    private String password;
}
