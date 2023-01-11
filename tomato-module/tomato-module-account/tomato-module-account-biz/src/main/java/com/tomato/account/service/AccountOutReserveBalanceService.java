package com.tomato.account.service;

import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.dao.AccountSettleDao;
import com.tomato.account.domain.entity.AccountHisEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 风险预存期外余额
 *
 * @author lizhifu
 * @since 2023/1/10
 */
@Service
@Slf4j
public class AccountOutReserveBalanceService {
    private final AccountSettleDao accountSettleDao;
    private final AccountHisDao accountHisDao;
    public AccountOutReserveBalanceService(AccountSettleDao accountSettleDao, AccountHisDao accountHisDao) {
        this.accountSettleDao = accountSettleDao;
        this.accountHisDao = accountHisDao;
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void exe(String accountNo, LocalDate date){
        // 1：根据账户号查账户信息
        // 5：计算值更改账户风险预存期外余额
        log.info("定时计算账户风险预存期外余额的账户号是[{}]日期是[{}]",accountNo,date);
        // 结算规则
        AccountSettleEntity accountSettle = accountSettleDao.selectByAccountNo(accountNo);
        if(null == accountSettle){
            log.info("定时计算账户风险预存期外余额不存在有效的结算记录,账户号:"+accountNo);
            return;
        }
        if(null == accountSettle.getReserveDays()){
            log.info("定时计算账户风险预存期外余额不存在风险预存期,账户号:"+accountNo);
            return;
        }
        // 风险预存期外日期
        LocalDate outDate = date.minusDays(accountSettle.getReserveDays() + 1);
        // 风险预存期内的账户入账 TODO
        // 所有的扣款都先扣风险预存期外的钱，日结通出款过程中风险预存期外的钱可能会成为负值，代扣代发、结算在扣款前已经做了金额校验
        // 情况1，退款 ：当天扣款，第二天才退款，此时风外的钱是否应该增加；因为扣款时扣的是风外的钱才能成功。
        // 情况2，提现 ：是否允许提取风外的钱，有个提取比例，和T0或者D0不同的是，还是存在风外、风内的钱；类似日结通产品
//        log.info("查询账户历史的开始时间[{}]结束时间[{}]",DateUtils.LONG_DATE_FORMAT.format(outReserveDay),date.toLocaleString());
//        List<AccountHisEntity> accountHisEntityList = accountHistoryService.queryCreditHistory(accountNo, outReserveDay,FundChangeDirectionEnum.INCREASE);
    }
}
