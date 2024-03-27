package com.tomato.operator.log.annotation;

import java.lang.annotation.*;

/**
 * 操作日志记录注解
 *
 * @author lizhifu
 * @since 2024/3/13
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogRecordAnnotation {
	/**
	 * 操作日志成功文本模板
	 */
	String success();

	/**
	 * 操作日志失败文本模板
	 */
	String fail() default "";

	/**
	 * 操作日志执行人
	 */
	String operator() default "";

	/**
	 * 操作日志标识
	 */
	String bizNo();

	/**
	 * 操作日志类型
	 */
	String category() default "";

	/**
	 * 操作日志详情
	 */
	String detail() default "";

	/**
	 * 记录操作日志条件
	 */
	String condition() default "";
}
