package com.tomato.sys.login.controller;

import com.tomato.captcha.domain.CaptchaResp;
import com.tomato.captcha.service.CaptchaService;
import com.tomato.domain.resp.SingleResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图形验证码业务
 *
 * @author lizhifu
 * @since 2022/12/28
 */
@RestController
public class CaptchaController {
    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping("/captcha/getCaptcha")
    public SingleResp<CaptchaResp> getCaptcha() {
        return SingleResp.of(captchaService.generateCaptcha());
    }
}
