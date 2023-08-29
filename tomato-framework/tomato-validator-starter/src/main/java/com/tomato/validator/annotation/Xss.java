package com.tomato.validator.annotation;

import com.tomato.validator.validator.XssValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * xss 校验注解
 *
 * @author lizhfiu
 * @since 2022年12月16日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Constraint(validatedBy = { XssValidator.class })
public @interface Xss {

	String message()

	default "不允许任何脚本运行";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
