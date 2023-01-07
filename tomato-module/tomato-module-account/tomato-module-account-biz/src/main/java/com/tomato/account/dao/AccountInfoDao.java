package com.tomato.account.dao;

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
     * 扣钱
     *
     * @param accountNo
     * @param amount
     * @param version
     * @return i
     */
    int deduct(@Param("accountNo") String accountNo, @Param("amount") BigDecimal amount, @Param("version") Integer version);

    /**
     * 加钱
     * @param accountNo
     * @param amount
     * @param version
     * @return
     */
    int add(@Param("accountNo") String accountNo, @Param("amount") BigDecimal amount,@Param("version") Integer version);

    /**
     * 冻结金额
     * @param accountNo
     * @param amount
     * @param version
     * @return
     */
    int freeze(@Param("accountNo") String accountNo, @Param("amount") BigDecimal amount,@Param("version") Integer version);

    /**
     * 解冻金额
     * @param accountNo
     * @param amount
     * @param version
     * @return
     */
    int unfreeze(@Param("accountNo") String accountNo, @Param("amount") BigDecimal amount,@Param("version") Integer version);
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
