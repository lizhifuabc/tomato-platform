package com.tomato.account.timer;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.dao.AccountSettleControlDao;
import com.tomato.account.service.AccountSettleService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AccountSettleTimer
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@SpringBootTest
public class AccountSettleTimerTest {

	@Resource
	AccountSettleTimer accountSettleTimer;

	@Resource
	AccountSettleService accountSettleService;

	@Resource
	AccountSettleControlDao accountSettleControlDao;

	@Resource
	AccountInfoDao accountInfoDao;

	@Test
	public void test() {
		accountSettleTimer.run();
	}

}
