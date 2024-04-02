package com.tomato.dynamic.db.annotation;

import java.lang.annotation.*;

/**
 * 多数据源切换注解 优先级：先方法，后类，如果方法覆盖了类上的数据源类型，以方法的为准，否则以类上的为准
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

	/**
	 * 切换数据源名称
	 */
	String value();

}
