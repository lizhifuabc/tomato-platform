package com.tomato.account.service;

import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.dao.AccountSettleDao;
import com.tomato.account.domain.bo.AccountHisCollectResBO;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.vo.enums.AccountHisTypeEnum;
import com.tomato.account.manager.AccountInfoManager;
import com.tomato.account.manager.AccountOutReserveBalanceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final AccountOutReserveBalanceManager accountOutReserveBalanceManager;
    private final AccountInfoManager accountInfoManager;
    public AccountOutReserveBalanceService(AccountSettleDao accountSettleDao, AccountHisDao accountHisDao, AccountOutReserveBalanceManager accountOutReserveBalanceManager, AccountInfoManager accountInfoManager) {
        this.accountSettleDao = accountSettleDao;
        this.accountHisDao = accountHisDao;
        this.accountOutReserveBalanceManager = accountOutReserveBalanceManager;
        this.accountInfoManager = accountInfoManager;
    }

    /**
     * 计算账户风险预存期外余额 TODO 异步执行 TODO 分页
     * 公式：风险预存期外余额 = 账户余额 - 风险预存期内的账户入账 + 风险预存期内的账户出账
     * <p>示例：当天时间：2023-04-20；风险预存期：1天；费率：0.1；
     * 交易1：2023-04-20 11:11:11 100元 手续费：10元
     * 交易2：2023-04-19 11:11:11 100元 手续费：10元
     * 交易3：2023-04-18 11:11:11 100元 手续费：10元
     * 交易4：2023-04-17 11:11:11 100元 手续费：10元
     * 此时：余额：360；风险预存期外余额：360 - 90 = 270；
     * 2023-04-20 提现 100元，手续费 40，余额：360 - （100+40）= 220；风险预存期外余额：270 - （100+40） = 130；
     * </p>
     * @param accountNo 账号
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void exe(String accountNo){
        LocalDate exeLocalDate = LocalDate.now();
        log.info("计算账户风险预存期外余额：开始：账户：{}，执行时间：{}",accountNo,exeLocalDate);
        // 账户查询
        AccountInfoEntity accountInfoEntity = accountInfoManager.selectByAccountNo(accountNo).orElseThrow(() -> new RuntimeException("账户不存在"));
        // 例如：
        // 当前时间：2023年01月11日
        // 风险预存期：2天
        // 风内账务历史：2023年01月10 <=  创建历史
        // 风内账务历史：当前时间 - （风险预存期 - 1） <=  创建历史
        // 结算规则
        AccountSettleEntity accountSettle = accountSettleDao.selectByAccountNo(accountNo);
        if(null == accountSettle){
            log.error("计算账户风险预存期外余额：失败：账户：{}，日期：{}，不存在有效的结算记录",accountNo,exeLocalDate);
            return;
        }
        if(null == accountSettle.getReserveDays()){
            log.error("计算账户风险预存期外余额：失败：账户：{}，日期：{}，不存在风险预存期",accountNo,exeLocalDate);
            return;
        }
        // 风险预存期内的账户入账 TODO
        // 所有的扣款都先扣风险预存期外的钱，日结通出款过程中风险预存期外的钱可能会成为负值，代扣代发、结算在扣款前已经做了金额校验
        // 情况1，退款 ：当天扣款，第二天才退款，此时风外的钱是否应该增加；因为扣款时扣的是风外的钱才能成功。
        // 情况2，提现 ：是否允许提取风外的钱，有个提取比例，和T0或者D0不同的是，还是存在风外、风内的钱；类似日结通产品

        // 风险预存期内日期
        // 计算规则：当前时间 - （风险预存期 - 1）
        // 例如：风险预存期：2天，当前时间：2023年01月11日，此时 2023年01月11日 和 2023年01月10 为风险预存期内的日期
        // 即：2023年01月11日 - （2 - 1） = 2023年01月10日
        LocalDate start = exeLocalDate.minusDays(accountSettle.getReserveDays() - 1);
        LocalDateTime startDate = start.atTime(LocalTime.MIN);
        LocalDateTime endDate = exeLocalDate.atTime(LocalTime.MAX);

        log.info("计算账户风险预存期外余额：查询账户历史：账户：{}，开始日期：{}，结束日期：{}，风险预存期为：{}",accountNo,startDate,endDate,accountSettle.getReserveDays());
        // 风内：
        // 加款：交易、退款
        // 扣款：提现、结算
        // 只统计加款里面的交易，退款不算收入；
        AccountHisCollectResBO collect = accountHisDao.collect(accountNo,startDate, endDate, AccountHisTypeEnum.TRAD.getValue());
        // 账户余额 - 账户历史金额
        // TODO 是否存在未入账的账户历史，如果存在，此时余额是少的，此时计算是有问题的。
        BigDecimal amount = accountInfoEntity.getBalance().subtract(collect.getTotalAmount());
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            log.error("计算账户风险预存期外余额：失败：账户：{}，开始日期：{}，结束日期：{}，风险预存期外余额小于0，查看是否存在未入账的账户历史",accountNo,startDate,endDate);
            return;
        }
        accountOutReserveBalanceManager.updateOutReserveBalance(accountInfoEntity.getAccountNo(),amount,accountInfoEntity.getVersion(),exeLocalDate);

        log.info("计算账户风险预存期外余额：成功：账户：{}，开始日期：{}，结束日期：{}，统计结果：{}",accountNo,startDate,endDate,collect);
    }
}
