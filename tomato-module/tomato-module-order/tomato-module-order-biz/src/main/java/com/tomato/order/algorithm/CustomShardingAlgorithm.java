package com.tomato.order.algorithm;

import com.tomato.order.util.HashShardingValueUtil;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;

/**
 * 自定义分库分表算法
 * 目前处理 = 和 in 操作，其余的操作，比如 >、< 等不支持。
 * @author lizhifu
 * @since 2023/1/1
 */
public class CustomShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {
    /**
     * 商户订单号
     */
    private static final String COLUMN_MERCHANT_ORDER_NO = "merchant_order_no";
    /**
     * 商户编号
     */
    private static final String COLUMN_MERCHANT_NO = "merchant_no";
    /**
     * 订单号
     */
    private static final String COLUMN_ORDER_NO = "order_no";
    /** 配置值需要储存 */
    private Properties props;

    /**
     * sharding
     * @param availableTargetNames 目标值
     * @param complexKeysShardingValue 值
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> complexKeysShardingValue) {
        if (!complexKeysShardingValue.getColumnNameAndRangeValuesMap().isEmpty()) {
            throw new RuntimeException("不支持除了=和in的操作");
        }
        // 订单号
        Collection<String> orderNos = complexKeysShardingValue.getColumnNameAndShardingValuesMap().getOrDefault(COLUMN_ORDER_NO, new ArrayList<>(1));
        // 商户订单号
        Collection<String> merchantOrderNos = complexKeysShardingValue.getColumnNameAndShardingValuesMap().getOrDefault(COLUMN_MERCHANT_ORDER_NO, new ArrayList<>(1));
        // 商户编号
        Collection<String> merchantNos = complexKeysShardingValue.getColumnNameAndShardingValuesMap().getOrDefault(COLUMN_MERCHANT_NO, new ArrayList<>(1));
        String dbSuffix;
        if(orderNos.isEmpty()){
            dbSuffix= String.valueOf(HashShardingValueUtil.dbSuffix(
                    merchantOrderNos.stream().findFirst().get(),
                    merchantNos.stream().findFirst().get(),availableTargetNames.size()));

        }else {
            dbSuffix = orderNos.stream()
                    // 截取后2位
                    .map(HashShardingValueUtil::dbIndex)
                    .findFirst().get();
        }
        String dataNode = availableTargetNames.stream().filter(targetName -> targetName.endsWith(dbSuffix)).findFirst().orElse(null);
        return Collections.singleton(dataNode);
    }
    @Override
    public void init(Properties props) {
        this.props = props;
    }
    @Override
    public String getType() {
        // 算法类型
        return "CUSTOM_SHARDING";
    }

    @Override
    public Properties getProps() {
        return props;
    }

}
