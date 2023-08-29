package com.tomato.account.timer;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AccountTimer
 *
 * @author lizhifu
 * @since 2023/8/23
 */
@SpringBootTest
public class AccountTimerTest {

	@Resource
	private AccountTimer accountTimer;

	@Test
	public void test() {
		accountTimer.run();
	}

}
