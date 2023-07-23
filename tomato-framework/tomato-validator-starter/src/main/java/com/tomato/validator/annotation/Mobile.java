package com.tomato.validator.annotation;


import com.tomato.validator.validator.MobileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 手机号校验
 *
 * @author lizhifu
 * @since  2021/12/10
 */
@Documented
// 指定真正实现校验规则的类
@Constraint(validatedBy = MobileValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Mobile {
    String message() default "不是正确的手机号码";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ElementType.METHOD,ElementType.FIELD,ElementType.PACKAGE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Mobile[] value();
    }
}

