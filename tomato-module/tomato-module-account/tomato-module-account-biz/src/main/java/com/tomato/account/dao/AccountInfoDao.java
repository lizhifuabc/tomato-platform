package com.tomato.account.dao;

import com.tomato.account.domain.bo.AccountBalanceBO;
import com.tomato.account.domain.entity.AccountInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 账户操作
 *
 * @author lizhifu
 * @since 2022/12/31
 */
@Mapper
public interface AccountInfoDao {
    /**
     * 查询账户
     *
     * @param accountNo
     * @return
     */
    AccountInfoEntity selectByAccountNo(@Param("accountNo") String accountNo);

    /**
     * 查询账户
     *
     * @param merchantNo 商编
     * @return 账户
     */
    AccountInfoEntity selectByMerchantNo(@Param("merchantNo") String merchantNo,@Param("accountType") String accountType);
    /**
     * 常规扣钱，校验风外金额
     *
     * @param accountBalanceBO 账户金额操作
     * @return i
     */
    int deduct(@Param("accountBalanceBO") AccountBalanceBO accountBalanceBO);
    /**
     * 非常规扣钱，风外金额可为负值，只要有余额就可以扣
     * 风外金额为负值时，负数为风内已经使用金额
     *
     * @param accountBalanceBO 账户金额操作
     * @return i
     */
    int deductSpecial(@Param("accountBalanceBO") AccountBalanceBO accountBalanceBO);

    /**
     * 加钱
     * @param accountBalanceBO 账户金额操作
     * @return 结果
     */
    int add(@Param("accountBalanceBO") AccountBalanceBO accountBalanceBO);

    /**
     * 冻结金额
     * @param accountNo
     * @param amount
     * @param version
     * @return
     */
    int freeze(@Param("accountNo") String accountNo, @Param("amount") BigDecimal amount,@Param("version") Integer version);

    /**
     * 更新风险预存期外余额
     * @param accountNo
     * @param outReserveBalance
     * @param version
     * @return
     */
    int updateOutReserveBalance(@Param("accountNo") String accountNo, @Param("outReserveBalance") BigDecimal outReserveBalance,@Param("version") Integer version);
    /**
     * 插入
     * @param AccountInfoEntity
     */
    void insert(AccountInfoEntity AccountInfoEntity);

    /**
     * 更新账户状态
     * @param accountNo 账户编号
     * @param accountStatus 状态
     * @param version 版本
     * @return
     */
    int updateAccountStatus(@Param("accountNo") String accountNo, @Param("accountStatus") String accountStatus,@Param("version") Integer version);
}
