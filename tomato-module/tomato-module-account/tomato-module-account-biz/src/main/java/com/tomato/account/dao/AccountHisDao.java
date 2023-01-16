package com.tomato.account.dao;

import com.tomato.account.domain.bo.*;
import com.tomato.account.domain.entity.AccountHisEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

/**
 * 账户历史
 *
 * @author lizhifu
 * @since 2022/12/31
 */
@Mapper
public interface AccountHisDao {
    /**
     * 查询账户历史表  分库分表，需要增加 accountNo,或者定制 accountHisId 也作为分表字段
     * @param id id
     * @return 账户历史
     */
    AccountHisEntity selectById(@Param("id") Long id, @Param("accountNo") String accountNo);

    /**
     * 查询账户历史表
     * @param merchantNo 商编
     * @param thirdNo 第三方流水号
     * @return 账户历史
     */
    AccountHisEntity selectByThirdNo(@Param("merchantNo") String merchantNo,@Param("thirdNo") String thirdNo);

    /**
     * 查询账户历史
     * @param accountNo
     * @param thirdNo
     * @return
     */
    @Select("select count(*) from account_his where account_no = #{accountNo} and third_no = #{thirdNo} limit 1")
    boolean checkThirdNo(@Param("accountNo") String accountNo,@Param("thirdNo") String thirdNo);
    /**
     * 新增账户历史表
     *
     * @param accountHisEntity 账户历史表
     * @return 新增账户历史表数量
     */
    int insert(AccountHisEntity accountHisEntity);

    /**
     * 批量更新账户历史状态  分库分表，需要增加 accountNo,或者定制 accountHisId 也作为分表字段
     * @param accountHisUpdateBatchDO
     * @return
     */
    int updateAccountStatusBatch(AccountHisUpdateBatchBO accountHisUpdateBatchDO);

    /**
     * 查询未入账的账户历史
     * account_status = 'DEAL'
     * @param accountNo
     * @return
     */
    AccountHisDealBO selectDeal(@Param("accountNo") String accountNo);

    /**
     * 日汇总账户待结算金额
     * @param accountNo 账号
     * @param start 创建时间开始
     * @param end 创建时间结束
     * @return
     */
    AccountHisCollectResBO collect(@Param("accountNo") String accountNo, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
