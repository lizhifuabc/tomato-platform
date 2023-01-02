package com.tomato.order;

import java.util.TreeMap;

/**
 * ShardCfg
 *
 * @author lizhifu
 * @since 2023/1/2
 */
public class ShardTest {
    private TreeMap<Long, Integer> nodeTreeMap = new TreeMap<>();
    private static final int DB_CNT = 32;
    private static final int TBL_CNT = 32;
    public static void main(String[] args) {
        System.out.println(shard("10000000000"));
    }

    public static ShardCfg shard(String userId) {
        int dbIdx = Math.abs(userId.hashCode() % DB_CNT);
        // 计算表序号时先剔除掉公约数的影响
        int tblIdx = Math.abs((userId.hashCode() / TBL_CNT) % TBL_CNT);
        return new ShardCfg(dbIdx, tblIdx);
    }
}
