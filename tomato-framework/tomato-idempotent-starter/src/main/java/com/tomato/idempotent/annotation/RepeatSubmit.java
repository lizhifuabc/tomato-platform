package com.tomato.idempotent.annotation;

import java.lang.annotation.*;

/**
 * 表单重复提交注解
 *
 * @author lizhifu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RepeatSubmit {

	/**
	 * 重复提交间隔时间/毫秒 默认1秒
	 */
	int value() default 1000;

	/**
	 * 最长间隔30s
	 */
	int MAX_INTERVAL = 30000;

}
