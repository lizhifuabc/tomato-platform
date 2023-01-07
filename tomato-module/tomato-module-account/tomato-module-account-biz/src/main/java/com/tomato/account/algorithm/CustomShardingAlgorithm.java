//package com.tomato.account.algorithm;
//
//import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
//import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * 自定义分库分表算法
// * 目前处理 = 和 in 操作，其余的操作，比如 >、< 等不支持。
// * @author lizhifu
// * @since 2023/1/1
// */
//public class CustomShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {
//    /**
//     * 账户编号
//     */
//    private static final String COLUMN_ACCOUNT_NO = "account_no";
//    /**
//     * 商户编号
//     */
//    private static final String COLUMN_MERCHANT_NO = "merchant_no";
//    /** 配置值需要储存 */
//    private Properties props;
//    @Override
//    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<String> complexKeysShardingValue) {
//        if (!complexKeysShardingValue.getColumnNameAndRangeValuesMap().isEmpty()) {
//            throw new RuntimeException("不支持除了=和in的操作");
//        }
//        // 账户编号
//        Collection<String> accountNos = complexKeysShardingValue.getColumnNameAndShardingValuesMap().getOrDefault(COLUMN_ACCOUNT_NO, new ArrayList<>(1));
//        // 商户编号
//        Collection<String> merchantNos = complexKeysShardingValue.getColumnNameAndShardingValuesMap().getOrDefault(COLUMN_MERCHANT_NO, new ArrayList<>(1));
//        // 整合账户编号、商户编号
//        List<String> ids = new ArrayList<>(16);
//        ids.addAll(accountNos);
//        ids.addAll(merchantNos);
//        return ids.stream()
//                // 截取后4位
//                .map(id -> id.substring(id.length() - 4))
//                // 去重
//                .distinct()
//                // 转换成int
//                .map(Integer::valueOf)
//                // 对可用的表名求余数，获取到真实的表的后缀
//                .map(idSuffix -> idSuffix % collection.size())
//                // 转换成string
//                .map(String::valueOf)
//                // 获取到真实的表
//                .map(tableSuffix -> collection.stream().filter(targetName -> targetName.endsWith(tableSuffix)).findFirst().orElse(null))
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//    }
//    @Override
//    public void init(Properties properties) {
//        this.props = properties;
//    }
//    @Override
//    public String getType() {
//        // 算法类型
//        return "CUSTOM_SHARDING";
//    }
//
//    @Override
//    public Properties getProps() {
//        return props;
//    }
//}
