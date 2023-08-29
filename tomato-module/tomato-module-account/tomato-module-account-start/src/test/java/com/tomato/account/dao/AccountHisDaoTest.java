package com.tomato.account.dao;

import com.tomato.account.domain.bo.AccountHisDealQueryBO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AccountHisDao
 *
 * @author lizhifu
 * @since 2023/5/3
 */
@SpringBootTest
public class AccountHisDaoTest {

	@Resource
	AccountHisDao accountHisDao;

	@Test
	public void allTest() {
		String accountNo = "102023081632289452901";
		AccountHisDealQueryBO accountHisDealQueryBO = new AccountHisDealQueryBO();
		accountHisDealQueryBO.setAccountNo(accountNo);
		accountHisDealQueryBO.setLimit(50000);
		System.out.println("selectDeal:" + accountHisDao.selectDeal(accountHisDealQueryBO));
	}

}
