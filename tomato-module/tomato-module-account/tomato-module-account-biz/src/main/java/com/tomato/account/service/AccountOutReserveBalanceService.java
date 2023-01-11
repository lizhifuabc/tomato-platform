package com.tomato.account.service;

import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.dao.AccountSettleDao;
import com.tomato.account.domain.bo.AccountHisCollectResBO;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private final AccountInfoDao accountInfoDao;
    public AccountOutReserveBalanceService(AccountSettleDao accountSettleDao, AccountHisDao accountHisDao, AccountInfoDao accountInfoDao) {
        this.accountSettleDao = accountSettleDao;
        this.accountHisDao = accountHisDao;
        this.accountInfoDao = accountInfoDao;
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void exe(String accountNo){
        // 账户查询
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByAccountNo(accountNo);
        // 例如：
        // 当前时间：2023年01月11日
        // 风险预存期：2天
        // 风内账务历史：2023年01月10 <=  创建历史
        // 风内账务历史：当前时间 - （风险预存期 - 1） <=  创建历史
        LocalDate now = LocalDate.now();
        log.info("账户[{}]计算账户风险预存期外余额的日期[{}]",accountNo,now);
        // 结算规则
        AccountSettleEntity accountSettle = accountSettleDao.selectByAccountNo(accountNo);
        if(null == accountSettle){
            log.warn("计算账户风险预存期外余额不存在有效的结算记录,账户号:"+accountNo);
            return;
        }
        if(null == accountSettle.getReserveDays()){
            log.warn("计算账户风险预存期外余额不存在风险预存期,账户号:"+accountNo);
            return;
        }
        // 风险预存期内日期
        LocalDate inDate = now.minusDays(accountSettle.getReserveDays() - 1);
        // 风险预存期内的账户入账 TODO
        // 所有的扣款都先扣风险预存期外的钱，日结通出款过程中风险预存期外的钱可能会成为负值，代扣代发、结算在扣款前已经做了金额校验
        // 情况1，退款 ：当天扣款，第二天才退款，此时风外的钱是否应该增加；因为扣款时扣的是风外的钱才能成功。
        // 情况2，提现 ：是否允许提取风外的钱，有个提取比例，和T0或者D0不同的是，还是存在风外、风内的钱；类似日结通产品
        log.info("查询账户历史:开始时间[{}]结束时间[{}]",inDate.atTime(LocalTime.MIN),now.atTime(LocalTime.MAX));
        AccountHisCollectResBO collect = accountHisDao.collect(accountNo,inDate.atTime(LocalTime.MIN), now.atTime(LocalTime.MAX));
        log.info("查询账户历史:开始时间[{}]结束时间[{}],结果:[{}]",inDate.atTime(LocalTime.MIN),now.atTime(LocalTime.MAX),collect);
        accountInfoDao.updateOutReserveBalance(accountInfoEntity.getAccountNo(),accountInfoEntity.getBalance().subtract(collect.getTotalAmount()),accountInfoEntity.getVersion());
    }
}
