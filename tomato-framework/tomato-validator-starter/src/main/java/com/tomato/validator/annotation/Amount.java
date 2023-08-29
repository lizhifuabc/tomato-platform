package com.tomato.validator.annotation;

import com.tomato.validator.validator.AmountValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义金额注解
 *
 * @author lizhifu
 * @date 2021/9/14
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
// 指定真正实现校验规则的类
@Constraint(validatedBy = AmountValidator.class)
public @interface Amount {

	String message() default "金额格式错误";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
