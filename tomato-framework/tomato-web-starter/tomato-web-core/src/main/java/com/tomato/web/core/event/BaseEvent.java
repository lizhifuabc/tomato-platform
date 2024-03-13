package com.tomato.web.core.event;

import lombok.Data;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * 功能描述: 事件基类
 *
 * @author lizhifu
 * @since 2024/3/13
 */
@Data
public class BaseEvent<T> implements ResolvableTypeProvider {
	/**
	 * 事件数据
	 */
	private T data;

	@Override
	public ResolvableType getResolvableType() {
		// 获取泛型类型
		// https://docs.spring.io/spring-framework/reference/core/beans/context-introduction.html#context-functionality-events-generics
		return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getData()));
	}
}
