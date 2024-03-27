package com.tomato.operator.log.autoconfigure;

import com.tomato.operator.log.service.OperatorLogRecordService;
import com.tomato.operator.log.service.OperatorUserService;
import com.tomato.operator.log.service.impl.DefaultOperatorLogRecordServiceImpl;
import com.tomato.operator.log.service.impl.DefaultOperatorUserServiceImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

/**
 * 自动配置
 *
 * @author lizhifu
 * @since 2024/3/13
 */
@AutoConfiguration
public class OperatorLogAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean(OperatorLogRecordService.class)
	@Role(BeanDefinition.ROLE_APPLICATION)
	public OperatorLogRecordService operatorLogRecordService() {
		return new DefaultOperatorLogRecordServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean(OperatorUserService.class)
	@Role(BeanDefinition.ROLE_APPLICATION)
	public OperatorUserService operatorUserService() {
		return new DefaultOperatorUserServiceImpl();
	}

}
