package com.tomato.mybatis.flex.config;

import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * PageHelper ，兼用老项目
 *
 * @author lizhifu
 * @since 2023/12/13
 */
@Configuration
public class PageHelperConfig {
	@Bean
	public PageInterceptor pageInterceptor(){
        return new PageInterceptor();
	}
}
