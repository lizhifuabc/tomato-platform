package com.tomato.order.util;

import com.tomato.util.StrUtil;

/**
 * 订单编号生成策略
 * 订单号 = 雪花|分库编号2位|分表编号2位|
 * 221220-545353963062231
 * 时间戳+用户标识码+随机数
 * 1672579493068
 * @author lizhifu
 * @date 2022/12/1
 */
public class OrderNoGenUtil {
    /**
     * db 数量
     */
    private final int dbCount;
    /**
     * table 数量
     */
    private final int tableCount;
    private final OrderSequence orderSequence;

    /**
     *
     * @param datacenterId 所属机器id
     * @param workerId 所属机器id
     * @param dbCount db 数量
     * @param tableCount table 数量
     */
    public OrderNoGenUtil(long datacenterId, long workerId, int dbCount, int tableCount) {
        this.dbCount = dbCount;
        this.tableCount = tableCount;
        this.orderSequence = new OrderSequence(workerId,datacenterId);
    }

    public String genOrderNo(String merchantOrderNo,String merchantNo){
        long dbSuffix = HashShardingValueUtil.dbSuffix(merchantOrderNo,merchantNo,dbCount);
        long tableSuffix = HashShardingValueUtil.tableSuffix(merchantOrderNo,merchantNo,tableCount);
        String no = orderSequence.nextId() +
                StrUtil.fillAfter("0", String.valueOf(dbSuffix), 2) +
                StrUtil.fillAfter("0", String.valueOf(tableSuffix), 2);
        return no;
    }

    public static void main(String[] args) {

    }
}
