package com.tomato.gen.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 代码生成配置
 *
 * @author lizhifu
 */
@Component
@ConfigurationProperties(prefix = "gen")
@Data
public class GenConfig {

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 生成包路径
	 */
	private String entityPackageName;

	/**
	 * 生成包路径
	 */
	private String respPackageName;

	/**
	 * 生成包路径
	 */
	private String reqPackageName;

	/**
	 * 生成包路径
	 */
	private String daoPackageName;

	/**
	 * 创建时间
	 */
	private LocalDateTime localDateTime = LocalDateTime.now();

	/**
	 * 前缀
	 */
	private String prefix;

}
