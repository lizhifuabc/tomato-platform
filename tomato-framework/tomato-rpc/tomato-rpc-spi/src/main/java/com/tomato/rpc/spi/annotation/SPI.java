package com.tomato.rpc.spi.annotation;

/**
 * 标记当前接口为可拓展接口
 * <p >@SPI("impl1")表示该接口点默认实现类为"impl1"
 * @author lizhifu
 * @since 2023/7/11
 */
public @interface SPI {
    /**
     * 默认实现方式
     */
    String value() default "";
}
