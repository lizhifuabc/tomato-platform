package com.tomato.gateway.handler;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 验证码生成逻辑处理类
 *
 * @author lizhifu
 * @since  2022/5/26
 */
@Slf4j
@Service
public class CodeHandler implements HandlerFunction<ServerResponse> {
	/**
	 * 验证码前缀
	 */
	private static final String CODE_KEY = "CODE_KEY:";
	/**
	 * 验证码有效期,默认 60秒
	 */
	private static final long CODE_TIME = 60;
	private static final Integer DEFAULT_IMAGE_WIDTH = 100;

	private static final Integer DEFAULT_IMAGE_HEIGHT = 40;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {
		Captcha captcha = new ArithmeticCaptcha(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
		// 几位数运算，默认是两位
		captcha.setLen(2);
		// 验证码文本
		String captchaText = captcha.text();
		// 验证码图片Base64字符串
		String captchaBase64 = captcha.toBase64();
		log.info("验证码:{}", captchaText);
		// 保存验证码信息
		Optional<String> randomStr = serverRequest.queryParam("randomStr");
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		randomStr.ifPresent(s -> redisTemplate.opsForValue()
			.set(CODE_KEY + s, captchaText, CODE_TIME, TimeUnit.SECONDS));

		// 转换流信息写出
		FastByteArrayOutputStream os = new FastByteArrayOutputStream();
		captcha.out(os);

		return ServerResponse.status(HttpStatus.OK)
			.contentType(MediaType.IMAGE_JPEG)
			.body(BodyInserters.fromResource(new ByteArrayResource(os.toByteArray())));
	}

}
