package com.tomato.mybatis.flex;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.logicdelete.LogicDeleteProcessor;
import com.mybatisflex.core.logicdelete.impl.DateTimeLogicDeleteProcessor;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * mybatis-flex自动配置
 *
 * @author lizhifu
 * @since 2023/12/13
 */
@AutoConfiguration
@Slf4j
public class TomatoMybatisFlexAutoConfiguration implements InitializingBean, MyBatisFlexCustomizer {

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("tomato-mybatis-flex-starter 自动装配");
	}


	@Bean
	public LogicDeleteProcessor logicDeleteProcessor(){
		log.info("tomato-mybatis-flex-starter 自动装配 LogicDeleteProcessor");
		return new DateTimeLogicDeleteProcessor();
	}

	@Override
	public void customize(FlexGlobalConfig flexGlobalConfig) {

		// 开启审计功能
		AuditManager.setAuditEnable(true);
		log.info("tomato-mybatis-flex-starter 自动装配 AuditManager");

		// 设置 SQL 审计收集器
		AuditManager.setMessageCollector(auditMessage ->
				log.info("{},{}ms", auditMessage.getFullSql()
						, auditMessage.getElapsedTime())
		);
		log.info("tomato-mybatis-flex-starter 自动装配 AuditManager.setMessageCollector");

		// 全局配置乐观锁字段 version
		flexGlobalConfig.setVersionColumn("version");
		log.info("tomato-mybatis-flex-starter 自动装配 全局配置乐观锁字段 version");

		// 正常状态的值为 0， 已删除 的值为 1
		flexGlobalConfig.setLogicDeleteColumn("del_flag");
		log.info("tomato-mybatis-flex-starter 自动装配 全局配置逻辑删除字段 del_flag");
	}
}
