package com.tomato.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 启动类
 * @author lizhifu
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:bean.xml"})
@EnableJpaAuditing // 开启时间的支持
public class MerchantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchantApplication.class, args);
	}

}
