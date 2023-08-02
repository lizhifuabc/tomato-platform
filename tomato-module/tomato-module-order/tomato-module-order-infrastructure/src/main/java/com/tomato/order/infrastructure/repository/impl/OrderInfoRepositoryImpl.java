package com.tomato.order.infrastructure.repository.impl;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.domain.repository.OrderInfoRepository;
import com.tomato.order.infrastructure.mapper.OrderInfoMapper;
import com.tomato.order.infrastructure.mapper.dataobject.OrderInfoDO;
import com.tomato.web.core.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单新建
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@Repository
public class OrderInfoRepositoryImpl implements OrderInfoRepository {
    private final OrderInfoMapper orderInfoMapper;

    public OrderInfoRepositoryImpl(OrderInfoMapper orderInfoMapper) {
        this.orderInfoMapper = orderInfoMapper;
    }

    @Override
    public void createOrder(OrderInfoEntity orderInfoEntity) {
        OrderInfoDO orderInfoDO = new OrderInfoDO();
        BeanUtils.copyProperties(orderInfoEntity,orderInfoDO);
        orderInfoMapper.insertSelective(orderInfoDO);
    }

    @Override
    public int updateTimeOutOrder() {
        return orderInfoMapper.updateTimeOutOrder();
    }

    @Override
    public List<OrderInfoEntity> selectByCreateTime(int pageIndex, int pageSize, LocalDateTime createTime, String orderStatus) {
        List<OrderInfoDO> list = orderInfoMapper.selectByCreateTime(pageIndex, pageSize, createTime, orderStatus);
        return BeanUtil.copyList(list, OrderInfoEntity.class);
    }
}