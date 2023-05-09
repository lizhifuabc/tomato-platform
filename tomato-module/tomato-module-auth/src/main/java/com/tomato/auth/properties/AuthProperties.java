package com.tomato.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;

/**
 * auth 配置
 *
 * @author lizhifu
 * @since 2023/5/3
 */
@ConfigurationProperties(prefix = "tomato.auth")
@Data
public class AuthProperties {
    /**
     * 设置登录页面中用户名字段名称 – #usernameParameter
     * 缺省值 : username
     */
    private String usernameParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
    /**
     * 设置登录页面中密码字段名称 – #passwordParameter
     * 缺省值 : password
     */
    private String passwordParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
    /**
     * 设置登录页面中“记住我”的参数名称 – #rememberMeParameter
     * 缺省值 : remember-me
     */
    private String rememberMeParameter = AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY;
    /**
     * 设置登录页面URL，缺省值为 /login
     */
    private String loginPageUrl = "/login";
    /**
     * 设置登录失败跳转页面URL – #failureForwardUrl
     * 缺省值 :本机地址:端口号/login?error
     */
    private String failureForwardUrl;
    /**
     * 设置登录成功跳转页面URL – #successForwardUrl
     * TODO 不能使用网络地址，否则会报错
     */
    private String successForwardUrl;
    /**
     * 本地jwk配置
     */
    private LocalJwkProperties localJwk;
}
