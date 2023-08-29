package com.tomato.jackson.datamask;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 数据脱敏
 *
 * @author lizhifu
 * @date 2022/12/12
 */
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = DataMaskingSerializer.class)
public @interface DataMask {

	DataMaskEnum function();

}
