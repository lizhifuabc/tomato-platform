package com.tomato.order.infrastructure.converter;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.infrastructure.dataobject.OrderInfoDO;
import com.tomato.order.infrastructure.dataobject.UpdateOrderStatusDO;

import java.util.List;

/**
 * <p>
 * 订单转换器
 * </p>
 *
 * @author lizhifu
 * @since 2023/8/9
 */
public class OrderConverter {

	private final static OrderInfoConverter CONVERTER = new OrderInfoConverter();

	private final static OrderInfoListConverter LIST_CONVERTER = new OrderInfoListConverter();

	private final static UpdateOrderStatusDOConverter UPDATE_ORDER_STATUS_DO_CONVERTER = new UpdateOrderStatusDOConverter();

	private final static OrderInfoDOConverter DO_CONVERTER = new OrderInfoDOConverter();

	/**
	 * 转换
	 * @param orderInfoDO 订单
	 * @return 订单
	 */
	public static OrderInfoEntity convert(OrderInfoDO orderInfoDO) {
		return CONVERTER.convert(orderInfoDO);
	}

	/**
	 * 转换
	 * @param list 订单
	 * @return 订单
	 */
	public static List<OrderInfoEntity> convert(List<OrderInfoDO> list) {
		return LIST_CONVERTER.convert(list);
	}

	/**
	 * 转换
	 * @param orderInfoEntity 订单
	 * @return 订单
	 */
	public static OrderInfoDO convert(OrderInfoEntity orderInfoEntity) {
		return DO_CONVERTER.convert(orderInfoEntity);
	}

	/**
	 * 转换
	 * @param orderInfoEntity 订单
	 * @return 订单
	 */
	public static UpdateOrderStatusDO convertUpdateOrderStatus(OrderInfoEntity orderInfoEntity) {
		return UPDATE_ORDER_STATUS_DO_CONVERTER.convert(orderInfoEntity);
	}

}
