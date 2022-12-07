package com.tomato.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 启动类
 * @author lizhifu
 */
@SpringBootApplication
@EnableJpaAuditing // 开启时间的支持
//@EntityScan(basePackages = {"com.tomato"})
public class MerchantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchantApplication.class, args);
	}

}
