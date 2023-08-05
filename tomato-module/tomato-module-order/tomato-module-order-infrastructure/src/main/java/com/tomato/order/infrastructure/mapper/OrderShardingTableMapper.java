package com.tomato.order.infrastructure.mapper;

import com.tomato.order.infrastructure.dataobject.OrderShardingTableDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单分表
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Mapper
public interface OrderShardingTableMapper {
    @Select("select * from t_order_sharding_table")
    List<OrderShardingTableDO> selectAll();
}
