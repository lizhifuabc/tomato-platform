package com.tomato.account.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

/**
 * AccountDailyCollectService
 *
 * @author lizhifu
 * @since 2023/8/23
 */
@SpringBootTest
public class AccountDailyCollectServiceTest {

	@Resource
	AccountDailyCollectService accountDailyCollectService;

	@Test
	public void test() {
		accountDailyCollectService.exe("102023081632289452901", LocalDate.now().minusDays(7));
	}

}
