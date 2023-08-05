package com.tomato.order.infrastructure.repository.impl;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.domain.repository.OrderInfoRepository;
import com.tomato.order.infrastructure.dataobject.OrderInfoIdxDO;
import com.tomato.order.infrastructure.mapper.OrderInfoIdxMapper;
import com.tomato.order.infrastructure.mapper.OrderInfoMapper;
import com.tomato.order.infrastructure.dataobject.OrderInfoDO;
import com.tomato.web.core.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    private final OrderInfoIdxMapper orderInfoIdxMapper;
    public OrderInfoRepositoryImpl(OrderInfoMapper orderInfoMapper, OrderInfoIdxMapper orderInfoIdxMapper) {
        this.orderInfoMapper = orderInfoMapper;
        this.orderInfoIdxMapper = orderInfoIdxMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(OrderInfoEntity orderInfoEntity) {
        OrderInfoDO orderInfoDO = new OrderInfoDO();
        BeanUtils.copyProperties(orderInfoEntity,orderInfoDO);
        orderInfoMapper.insertSelective(orderInfoDO);

        OrderInfoIdxDO orderInfoIdxDO = new OrderInfoIdxDO();
        BeanUtils.copyProperties(orderInfoEntity,orderInfoIdxDO);
        orderInfoIdxMapper.insertSelective(orderInfoIdxDO);
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

    @Override
    public OrderInfoEntity selectByOrderNo(String merchantNo, String orderNo) {
        OrderInfoDO orderInfoDO = orderInfoMapper.selectByMerchantOrderNo(orderNo,merchantNo);
        return convert(orderInfoDO);
    }

    @Override
    public OrderInfoEntity selectByMerchant(String merchantNo, String merchantOrderNo) {
        OrderInfoDO orderInfoDO = orderInfoMapper.selectByMerchant(merchantNo, merchantOrderNo);
        return convert(orderInfoDO);
    }
    private OrderInfoEntity convert(OrderInfoDO orderInfoDO) {
        return BeanUtil.copy(orderInfoDO, OrderInfoEntity.class);
    }
}