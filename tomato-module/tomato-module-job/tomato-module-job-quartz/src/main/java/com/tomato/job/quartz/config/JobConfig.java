package com.tomato.job.quartz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * JobConfig: 配置类
 *
 * @author lizhifu
 * @since 2023/9/17
 */
@Configuration
public class JobConfig {
	@Autowired
	private ApplicationContext applicationContext;

	@Value("${spring.quartz.auto-startup:true}")
	private boolean quartzAutoStartup;

	@Value("${spring.quartz.overwrite-existing-jobs:true}")
	private boolean quartzOverwriteExistingJobs;

	@Value("${spring.quartz.wait-for-jobs-to-complete-on-shutdown:true}")
	private boolean quartzWaitForJobsToCompleteOnShutdown;

}
