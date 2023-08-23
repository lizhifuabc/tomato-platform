package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountDailyCollectEntity;
import com.tomato.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;

/**
 * 每日待结算汇总
 *
 * @author lizhifu
 * @since 2022/12/31
 */
@Mapper
public interface AccountDailyCollectDao extends BaseMapper<AccountDailyCollectEntity, Long> {
    /**
     * 查询每日待结算汇总
     *
     * @param accountNo  账号
     * @param collectDate 日期
     * @return 每日待结算汇总
     */
    AccountDailyCollectEntity selectByAccountNoAndCollectDate(@Param("accountNo") String accountNo, @Param("collectDate") LocalDate collectDate);

//    /**
//     * 更新每日待结算汇总
//     * @param accountNo
//     * @param collectDate
//     * @param version
//     */
//    @Update("update account_daily_collect set sett_status = 0,version = version + 1 where account_no = #{accountNo} and collect_date = #{collectDate} and version = #{version}")
//    int updateSettStatus(@Param("accountNo") String accountNo,@Param("collectDate") LocalDate collectDate,@Param("version") Integer version);
//
//    /**
//     * 更新每日待结算汇总备注
//     * @param accountNo
//     * @param collectDate
//     * @param remark
//     * @return
//     */
//    @Update("update account_daily_collect set remark = #{remark} where account_no = #{accountNo} and collect_date = #{collectDate}")
//    int updateRemark(@Param("accountNo") String accountNo,@Param("collectDate") LocalDate collectDate,@Param("remark") String remark);
}
