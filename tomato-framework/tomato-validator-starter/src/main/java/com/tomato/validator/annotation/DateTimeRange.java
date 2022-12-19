package com.tomato.validator.annotation;

import com.tomato.validator.validator.DateTimeRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.temporal.ChronoUnit;

/**
 * 日期时间范围校验注解，可作用于LocalDateTime or 字符串型年月日时分秒（格式可通过pattern属性指定
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateTimeRangeValidator.class)
public @interface DateTimeRange {
    /**
     * 最小时间范围（为负数即为前n unit）
     */
    int min() default 0;

    int max() default Integer.MAX_VALUE;

    /**
     * 时间单位 (年月日)
     */
    ChronoUnit unit() default ChronoUnit.DAYS;

    /**
     * 作用于字符串时，指定的格式，包含年月日时分秒
     */
    String pattern() default "yyyy-MM-dd HH:mm:ss";
    /**
     * 错误提示
     */
    String message() default "日期时间错误";

    /**
     * 用于分组校验
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
