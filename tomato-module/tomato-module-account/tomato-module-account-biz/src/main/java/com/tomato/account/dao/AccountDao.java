package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * 账户操作
 *
 * @author lizhifu
 * @since 2022/12/31
 */
@Mapper
public interface AccountDao {
    /**
     * 查询账户
     *
     * @param accountNo
     * @return
     */
    AccountEntity selectByAccountNo(@Param("accountNo") String accountNo);

    /**
     * 查询账户
     *
     * @param merchantNo
     * @return
     */
    AccountEntity selectByMerchantNo(@Param("merchantNo") String merchantNo,@Param("status") Integer status);
    /**
     * 查询账户
     *
     * @param merchantNo
     * @return
     */
    AccountEntity selectByMerchantNoWithOutStatus(@Param("merchantNo") String merchantNo);
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
     * @param AccountEntity
     */
    void insert(AccountEntity AccountEntity);
}
