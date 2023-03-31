package com.tomato.gateway;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;

/**
 * Captcha
 *
 * @author lizhifu
 * @since 2023/3/31
 */
public class CaptchaTest {
    public static void main(String[] args) {
        Captcha captcha = new ArithmeticCaptcha(100, 40);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 验证码文本
        String captchaText = captcha.text();
        // 验证码图片Base64字符串
        String captchaBase64 = captcha.toBase64();
        System.out.println(captchaText);
    }
}
