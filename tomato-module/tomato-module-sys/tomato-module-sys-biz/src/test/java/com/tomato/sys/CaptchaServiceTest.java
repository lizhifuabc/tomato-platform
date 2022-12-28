package com.tomato.sys;

import com.tomato.captcha.service.CaptchaService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * CaptchaService
 *
 * @author lizhifu
 * @since 2022/12/28
 */
@SpringBootTest
public class CaptchaServiceTest {
    @Resource
    CaptchaService captchaService;

    @Test
    public void test(){
        System.out.println(captchaService.generateCaptcha());
    }
}
