package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountSettleControlEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 账户结算控制
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Mapper
public interface AccountSettleControlDao {
    /**
     * 插入
     * @param accountSettleControlEntity 账户结算控制
     */
    void insert(AccountSettleControlEntity accountSettleControlEntity);

    /**
     * 查询根据下次结算日小于等于指定日期且状态为可结算的结算控制记录
     * @param nextSettleDate 下次结算日期
     * @return 结算控制记录集合
     */
    List<AccountSettleControlEntity> selectAccount(@Param("nextSettleDate") LocalDate nextSettleDate);

    /**
     * 查询
     * @param id id
     * @return 实体
     */
    AccountSettleControlEntity selectById(@Param("id") Long id);

    /**
     * 根据账号查询
     * @param accountNo 账号
     * @return 实体
     */
    AccountSettleControlEntity selectByAccountNo(@Param("accountNo") String accountNo);

    /**
     * 更新结算控制
     * @param id id
     * @param nextSettleDate nextSettleDate
     * @param settleRecordId 账户结算记录ID
     * @param version version
     * @return 数量
     */
    int updateSettleControl(@Param("id") Long id,
                            @Param("nextSettleDate") LocalDate nextSettleDate,
                            @Param("settleRecordId") Long settleRecordId,
                            @Param("version") Integer version);
}
