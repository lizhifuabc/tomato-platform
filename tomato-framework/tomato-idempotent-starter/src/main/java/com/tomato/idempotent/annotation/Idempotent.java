package com.tomato.idempotent.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 幂等注解 幂等性（Idempotence）是指对同一操作进行多次执行，产生的效果和执行一次的效果相同。
 *
 * @author lizhifu
 * @since 2023/4/11
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

	/**
	 * 幂等的超时时间，默认为 1 秒，超过时间，请求释放
	 */
	int timeout() default 1;

	/**
	 * 时间单位，默认为 SECONDS 秒
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;

	/**
	 * 提示
	 */
	String message() default "操作过于频繁，请稍后检查结果。";

	/**
	 * 执行策略，默认为 REDIS
	 */
	String strategy() default "REDIS";

}
