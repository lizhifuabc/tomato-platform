package com.tomato.account.service;

import com.tomato.account.dao.AccountSettleControlDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 账号结算控制
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@Service
@Slf4j
public class AccountSettleService {
    private final AccountSettleControlDao accountSettleControlDao;

    public AccountSettleService(AccountSettleControlDao accountSettleControlDao) {
        this.accountSettleControlDao = accountSettleControlDao;
    }

    @Async("asyncTaskExecutorAccount")
    public void settle(LocalDate nextSettleDate,String accountNo){
        log.info("下次结算日期等于[{}]的账户[{}]开始跑结算定时",nextSettleDate,accountNo);

    }
}
