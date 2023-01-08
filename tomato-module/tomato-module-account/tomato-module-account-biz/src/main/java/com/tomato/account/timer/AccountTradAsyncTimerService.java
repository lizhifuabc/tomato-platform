package com.tomato.account.timer;

import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.domain.bo.AccountHisDealBO;
import com.tomato.account.domain.bo.AccountHisUpdateBatchBO;
import com.tomato.account.service.AccountAsyncService;
import com.tomato.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 账户异步入账服务
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Component
@Slf4j
public class AccountTradAsyncTimerService {
    private final AccountHisDao accountHisDao;
    private final AccountAsyncService accountAsyncService;
    public AccountTradAsyncTimerService(AccountHisDao accountHisDao, AccountAsyncService accountAsyncService) {
        this.accountHisDao = accountHisDao;
        this.accountAsyncService = accountAsyncService;
    }

    @Scheduled(fixedRate = 1000 * 60L)
    public void run() {

    }

    @Async("asyncTaskExecutorAccount")
    public void exe(String accountNo){
        AccountHisDealBO accountHisDealBO = accountHisDao.selectDeal(accountNo);
        log.info("账户异步入账服务 accountNo:{},accountHisDealBO:{}",accountNo,accountHisDealBO);
        if(accountHisDealBO == null){
            return;
        }
        AccountHisUpdateBatchBO accountHisUpdateBatchDO = BeanUtil.copy(accountHisDealBO,AccountHisUpdateBatchBO.class);
        accountHisUpdateBatchDO.setAccountNo(accountNo);
        accountAsyncService.async(accountHisUpdateBatchDO);
    }
}
