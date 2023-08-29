package com.tomato.order.infrastructure.mapper;

import com.tomato.mybatis.mapper.BaseMapper;
import com.tomato.order.infrastructure.dataobject.OrderInfoIdxDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单索引表
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Mapper
public interface OrderInfoIdxMapper extends BaseMapper<OrderInfoIdxDO, Long> {

}
