package com.tomato.account.util;

import com.tomato.account.vo.enums.CycleTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.OptionalInt;

/**
 * 结算日期计算
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Slf4j
public class SettleDayUtil {
    public static void main(String[] args) {
        // 设置当前日期为星期五
        LocalDate friday = LocalDate.parse("2023-08-12");
        // 跨周测试数据
        String[] cycleData2 = {"1", "2"};
        System.out.println("下次结算日期: " + nextSettleDateByWeek(cycleData2, friday));

        String[] cycleData = {"31"};
        LocalDate endOfMonth = LocalDate.parse("2023-01-31");
        System.out.println("下次结算日期: " + nextSettleDateByMonth(cycleData, endOfMonth));
    }
    public static LocalDate settleDate(String[] cycleData, LocalDate nextSettle,CycleTypeEnum cycleTypeEnum,int reserveDay){
        // 下次结算日<当前日期，取当前日期
        nextSettle = nextSettle.isBefore(LocalDate.now()) ? LocalDate.now() : nextSettle;
        LocalDate nextSettleDate = nextSettleDate(cycleData,nextSettle,cycleTypeEnum);
        // 风险时间 = 下次结算时间 - （风险预存期 + 1）
        // 防止长久未结算的情况
        // 当前时间：2023年01月08日
        // 下次结算时间：2023年01月20日
        // 风险时间 = 2023年01月20日 - （1 + 1）= 2023年01月18日
        // 当前时间 > 风险时间
        // TODO 增加循环控制
        while (LocalDate.now().isAfter(nextSettleDate.minusDays(reserveDay))){
            nextSettleDate = nextSettleDate(cycleData,nextSettleDate,cycleTypeEnum);
        }
        return nextSettleDate;
    }
    /**
     * 下一结算日计算
     * @param cycleData 结算日设置
     * @param nextSettle 当前值
     * @param cycleTypeEnum 周期类型
     * @return 下一结算日
     */
    private static LocalDate nextSettleDate(String[] cycleData, LocalDate nextSettle,CycleTypeEnum cycleTypeEnum){
        log.info("下一结算日计算，周期类型：{}, 结算日设置：{}, 当前值：{}",cycleTypeEnum,cycleData,nextSettle);
        return switch (cycleTypeEnum) {
            case MONTH, MONTH_WORK -> nextSettleDateByMonth(cycleData, nextSettle);
            case WEEK, WEEK_WORK -> nextSettleDateByWeek(cycleData, nextSettle);
        };
    }
    private static LocalDate nextSettleDateByWeek(String[] cycleData, LocalDate currentSettle) {
        // 获取当前结算日是周几
        int week = currentSettle.getDayOfWeek().getValue();
        // 获取下次结算日距离最近的结算日的天数
        int daysUntilNextSettle = Arrays.stream(cycleData)
                .mapToInt(Integer::parseInt)
                .filter(day -> day > week)
                .findFirst()
                .orElse(Integer.parseInt(cycleData[0]));
        DayOfWeek dayOfWeek = DayOfWeek.of(daysUntilNextSettle);
        return currentSettle.with(TemporalAdjusters.next(dayOfWeek));
    }
    private static LocalDate nextSettleDateByMonth(String[] cycleData,LocalDate currentSettle){
        // 当前结算日期是几号
        int month = currentSettle.getDayOfMonth();
        // 获取下次结算日距离最近的结算日的天数
        OptionalInt daysUntilNextSettle = Arrays.stream(cycleData)
                .mapToInt(Integer::parseInt)
                .filter(day -> day > month)
                .findFirst();
        // 如果存在，下一个结算日期，就是daysUntilNextSettle
       if (daysUntilNextSettle.isPresent()) {
            return currentSettle.withDayOfMonth(daysUntilNextSettle.getAsInt());
        }
        // 下次结算日加一个月
        currentSettle = currentSettle.plusMonths(1).withDayOfMonth(1);
        int daysInMonth = currentSettle.lengthOfMonth();
        return currentSettle.withDayOfMonth(Math.min(Integer.parseInt(cycleData[0]), daysInMonth));
    }
}
