package com.tomato.utils.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.function.Function;

/**
 * 事务工具
 *
 * @author lizhifu
 * @since 2023/9/4
 */
@Slf4j
public class TransactionalUtil {
	/**
	 * 事务执行方法
	 * @param propagationBehavior 事务传播方式
	 * @param parameter 参数
	 * @param processFun 处理方法
	 * @param defaultResult 默认返回
	 * @return R 返回
	 * @param <R> 返回类型
	 * @param <P> 参数类型
	 */
	public static <R,P> R runWithTransactional(int propagationBehavior,
												P parameter,
												Function<P,R> processFun,
												R defaultResult){
		// 开启事务
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(propagationBehavior);
		PlatformTransactionManager transactionManager = SpringContextUtil.getBean(PlatformTransactionManager.class);
		TransactionStatus transactionStatus = transactionManager.getTransaction(def);
		try {
			R result = processFun.apply(parameter);
			transactionManager.commit(transactionStatus);
			return result;
		}catch (Exception e){
			log.warn("事务执行异常:",e);
			transactionManager.rollback(transactionStatus);
			return defaultResult;
		}
	}

	/**
	 * 开启新事务</u>
	 * @param parameter 参数
	 * @param processFun 处理方法
	 * @param defaultResult 默认返回
	 * @return R 返回
	 * @param <R> 返回类型
	 * @param <P> 参数类型
	 */
	public static <R,P> R runWithNewTransactional(P parameter,
											   Function<P,R> processFun,
											   R defaultResult){
		return runWithTransactional(TransactionDefinition.PROPAGATION_REQUIRES_NEW,parameter,processFun,defaultResult);
	}
}
