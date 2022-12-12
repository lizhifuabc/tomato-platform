package com.tomato.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 敏感数据
 *
 * @author lizhifu
 * @date 2022/12/8
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SensitiveData {
    /**
     * 加密时从srcKey到destKey
     * @return
     */
    String[] srcKey() default {};

    /**
     * 解密时从destKey到srcKey
     * @return
     */
    String[] destKey() default {};
}
