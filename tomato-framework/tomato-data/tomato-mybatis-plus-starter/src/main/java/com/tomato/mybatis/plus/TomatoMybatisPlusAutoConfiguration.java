package com.tomato.mybatis.plus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * mybatis自动配置
 *
 * @author lizhifu
 * @since 2024/3/14
 */
@AutoConfiguration
@ComponentScan
@ConfigurationPropertiesScan
public class TomatoMybatisPlusAutoConfiguration {
	/**
	 * MybatisPlusInterceptor 插件
	 * @return MybatisPlusInterceptor
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 防止全表更新与删除插件
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

		// 乐观锁插件
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

		// 分页插件,如果配置多个插件,切记分页最后添
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}
}
