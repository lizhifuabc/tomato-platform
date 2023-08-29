package com.tomato.captcha.domain;

import lombok.Data;

/**
 * 图形验证码
 *
 * @author lizhifu
 */
@Data
public class CaptchaResp {

	/**
	 * 验证码唯一标识
	 */
	private String captchaUuid;

	/**
	 * 验证码Base64图片
	 */
	private String captchaBase64Image;

	/**
	 * 过期时间（秒）
	 */
	private Long expireSeconds;

	/**
	 * 验证码图片内容
	 */
	private String captchaText;

}
