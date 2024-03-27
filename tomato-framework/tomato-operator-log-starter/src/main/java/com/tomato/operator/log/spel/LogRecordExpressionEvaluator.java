package com.tomato.operator.log.spel;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模板解析
 *
 * @author lizhifu
 * @since 2024/3/13
 */
public class LogRecordExpressionEvaluator extends CachedExpressionEvaluator {
	private Map<ExpressionKey, Expression> expressionCache = new ConcurrentHashMap<>(64);

	private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

	public String parseExpression(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
		return getExpression(this.expressionCache, methodKey, conditionExpression).getValue(evalContext, String.class);
	}
}
