package com.tomato.order.infrastructure.repository.impl;

import com.tomato.common.exception.BusinessException;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.domain.repository.OrderInfoRepository;
import com.tomato.order.infrastructure.converter.OrderConverter;
import com.tomato.order.infrastructure.dataobject.OrderInfoIdxDO;
import com.tomato.order.infrastructure.mapper.OrderInfoIdxMapper;
import com.tomato.order.infrastructure.mapper.OrderInfoMapper;
import com.tomato.order.infrastructure.dataobject.OrderInfoDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.tomato.order.infrastructure.converter.OrderConverter.convert;
import static com.tomato.order.infrastructure.converter.OrderConverter.convertUpdateOrderStatus;

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
	public int updateOrderStatus(OrderInfoEntity orderInfoEntity) {
		int i = orderInfoMapper.updateOrderStatus(convertUpdateOrderStatus(orderInfoEntity));
		// 更新条数必须 = 1，否则回滚
		if (i != 1) {
			throw new BusinessException("更新订单状态失败");
		}
		return i;
	}

	@Override
	public OrderInfoEntity selectByOrderNo(String orderNo) {
		OrderInfoDO orderInfoDO = orderInfoMapper.selectByOrderNo(orderNo);
		return OrderConverter.convert(orderInfoDO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createOrder(OrderInfoEntity orderInfoEntity) {
		orderInfoMapper.insertSelective(convert(orderInfoEntity));

		OrderInfoIdxDO orderInfoIdxDO = new OrderInfoIdxDO();
		BeanUtils.copyProperties(orderInfoEntity, orderInfoIdxDO);
		orderInfoIdxMapper.insertSelective(orderInfoIdxDO);
	}

	@Override
	public int updateTimeOutOrder() {
		return orderInfoMapper.updateTimeOutOrder();
	}

	@Override
	public List<OrderInfoEntity> selectByCreateTime(int pageIndex, int pageSize, LocalDateTime createTime,
			String orderStatus) {
		List<OrderInfoDO> list = orderInfoMapper.selectByCreateTime(pageIndex, pageSize, createTime, orderStatus);
		return OrderConverter.convert(list);
	}

	@Override
	public OrderInfoEntity selectByOrderNo(String merchantNo, String orderNo) {
		OrderInfoDO orderInfoDO = orderInfoMapper.selectByMerchantOrderNo(orderNo, merchantNo);
		return convert(orderInfoDO);
	}

	@Override
	public OrderInfoEntity selectByMerchant(String merchantNo, String merchantOrderNo) {
		OrderInfoDO orderInfoDO = orderInfoMapper.selectByMerchant(merchantNo, merchantOrderNo);
		return convert(orderInfoDO);
	}

	@Override
	public int updateOrderStatusSuccess(OrderInfoEntity orderInfoEntity) {
		int i = orderInfoMapper.updateOrderStatusSuccess(convertUpdateOrderStatus(orderInfoEntity));
		// 更新条数必须 = 1，否则回滚
		if (i != 1) {
			throw new BusinessException("更新订单状态失败");
		}
		return i;
	}

}