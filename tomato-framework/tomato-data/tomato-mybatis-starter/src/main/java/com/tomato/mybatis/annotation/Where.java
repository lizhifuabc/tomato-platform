package com.tomato.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 给实体类查询增加where条件 常见用法如用来做来软删除
 * <p>
 * 示例：@Where(clause="del_flag = 0 ")
 *
 * @author lizhifu
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Where {

	/**
	 * The where-clause predicate.
	 */
	String clause();

}
