package com.tomato.order.infrastructure.repository.impl;

import com.tomato.order.domain.constants.ShardingConstant;
import com.tomato.order.domain.repository.ShardingRepository;
import com.tomato.order.infrastructure.mapper.ShardingDbMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 分库分表
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Repository
@Slf4j
public class ShardingRepositoryImpl implements ShardingRepository {
    private final ShardingDbMapper shardingDbMapper;

    public ShardingRepositoryImpl(ShardingDbMapper shardingDbMapper) {
        this.shardingDbMapper = shardingDbMapper;
    }

    @Override
    public String getShardingDb(String merchantNoSpilt) {
        return shardingDbMapper.selectShardingDb(merchantNoSpilt).orElse(ShardingConstant.DB_DEFAULT);
    }

    @Override
    public String getShardingTable(String merchantNoSpilt) {
        return null;
    }

    @Override
    public String getShardingDbByOrderNo(String orderNo) {
        return null;
    }

    @Override
    public String getShardingTableByOrderNo(String orderNo) {
        return null;
    }
}
