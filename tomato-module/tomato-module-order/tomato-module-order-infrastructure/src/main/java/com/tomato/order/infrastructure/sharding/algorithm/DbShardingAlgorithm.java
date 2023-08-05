package com.tomato.order.infrastructure.sharding.algorithm;

import com.google.common.base.Preconditions;
import com.tomato.order.domain.constants.ShardingConstant;
import com.tomato.order.domain.repository.ShardingRepository;
import com.tomato.order.infrastructure.holder.ApplicationContextHolder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;

/**
 * 分库算法
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Getter
@Slf4j
public class DbShardingAlgorithm implements ComplexKeysShardingAlgorithm<Comparable<String>> {
    private Properties props;
    /**
     * 分库算法
     * @param collection collection 在加载配置文件时，会解析分片规则。将结果存储到 collection中，doSharding（）参数使用
     * @param complexKeysShardingValue SQL中对应的分片键值
     * @return 返回分片后的库名
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<Comparable<String>> complexKeysShardingValue) {
        // 分片规则
        Collection<String> result = new LinkedHashSet<>(collection.size());
        String merchantNoSpilt = "";
        // 获取分片键值
        Map<String, Collection<Comparable<String>>> columnNameAndShardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        log.info("获取分片键值 columnNameAndShardingValuesMap:{}",columnNameAndShardingValuesMap);
        // 优先使用merchantNo分片
        String merchantNoSharding = "merchant_no";
        Collection<Comparable<String>> merchantNoCollection = columnNameAndShardingValuesMap.get(merchantNoSharding);
        if (merchantNoCollection != null && !merchantNoCollection.isEmpty()) {
            merchantNoSpilt = ShardingConstant.merchantNoSpilt(merchantNoCollection.stream().findFirst().get().toString());
        }
        // 其次使用orderNo分片
        String orderNoSharding = "order_no";
        Collection<Comparable<String>> orderNoCollection = columnNameAndShardingValuesMap.get(orderNoSharding);
        if (orderNoCollection != null && !orderNoCollection.isEmpty()) {
            merchantNoSpilt = ShardingConstant.orderNoSpilt(orderNoCollection.stream().findFirst().get().toString());
        }
        // 没有获取到分片值
        Preconditions.checkArgument(!merchantNoSpilt.isEmpty(), "没有获取到分片值");
        // 根据商户号分库，是否指定分库
        ShardingRepository shardingRepository = ApplicationContextHolder.getBean(ShardingRepository.class);
        String shardingDb = shardingRepository.getShardingDb(merchantNoSpilt);
        if (shardingDb.equals(ShardingConstant.DB_DEFAULT)) {
            // 没有指定分库，使用商户号分片
            result.add(ShardingConstant.DB_PREFIX + ShardingConstant.hashShardingValue(merchantNoSpilt) % ShardingConstant.SHARDING_COUNT);
            return result;
        }
        result.add(shardingDb);
        return result;
    }
    @Override
    public void init(Properties props) {
        this.props = props;
    }
}
