package com.tomato.captcha.service;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.tomato.captcha.domain.CaptchaResp;
import com.tomato.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.UUID;

/**
 * 图形验证码
 *
 * @author lizhifu
 * @since 2022/12/28
 */
@Slf4j
public class CaptchaService {
    /**
     * 过期时间：65秒
     */
    private static final long EXPIRE_SECOND = 65L;
    private final DefaultKaptcha defaultKaptcha;

    public CaptchaService(DefaultKaptcha defaultKaptcha) {
        this.defaultKaptcha = defaultKaptcha;
    }

    public CaptchaResp generateCaptcha() {
        String captchaText = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(captchaText);

        String base64Code;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", os);
            base64Code = Base64.getEncoder().encodeToString((os.toByteArray()));
        } catch (Exception e) {
            log.error("generateCaptcha error:", e);
            throw new BusinessException("生成验证码错误");
        }

        /**
         * 返回验证码对象
         * 图片 base64格式
         */
        // uuid 唯一标识
        String uuid = UUID.randomUUID().toString().replace("-", "");

        CaptchaResp captchaResp = new CaptchaResp();
        captchaResp.setCaptchaUuid(uuid);
        captchaResp.setCaptchaText(captchaText);
        captchaResp.setCaptchaBase64Image("data:image/png;base64," + base64Code);
        captchaResp.setExpireSeconds(EXPIRE_SECOND);
        return captchaResp;
    }
}
