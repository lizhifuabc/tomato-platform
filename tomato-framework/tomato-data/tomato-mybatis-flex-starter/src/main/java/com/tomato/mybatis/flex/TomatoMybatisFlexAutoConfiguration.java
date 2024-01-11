package com.tomato.mybatis.flex;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.logicdelete.LogicDeleteProcessor;
import com.mybatisflex.core.logicdelete.impl.DateTimeLogicDeleteProcessor;
import com.mybatisflex.core.logicdelete.impl.TimeStampLogicDeleteProcessor;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import com.tomato.mybatis.flex.config.PageHelperConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * mybatis-flex自动配置
 *
 * @author lizhifu
 * @since 2023/12/13
 */
@AutoConfiguration
@ImportAutoConfiguration(classes = {PageHelperConfig.class})
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

		// 解决逻辑删除的唯一索引冲突问题
		LogicDeleteManager.setProcessor(new TimeStampLogicDeleteProcessor());
		flexGlobalConfig.setLogicDeleteColumn("del_flag");
		log.info("tomato-mybatis-flex-starter 自动装配 全局配置逻辑删除字段 del_flag");
	}
}
