package com.tomato.merchant;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;

/**
 * merchantIncrementer
 *
 * @author lizhifu
 * @since 2022/12/15
 */
@SpringBootTest
public class MerchantIncrementerTest {

	@Resource
	MySQLMaxValueIncrementer merchantIncrementer;

	@Test
	public void test() {
		System.out.println(merchantIncrementer.nextLongValue());
	}

}
