package com.tomato.order.infrastructure.converter;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.infrastructure.dataobject.OrderInfoDO;
import com.tomato.web.core.util.BeanUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>
 * 订单转换器
 * </p>
 *
 * @author lizhifu
 * @since 2023/8/24
 */
public class OrderInfoConverter implements Converter<OrderInfoDO, OrderInfoEntity> {

	@Override
	public OrderInfoEntity convert(@NotNull OrderInfoDO source) {
		return BeanUtil.copy(source, OrderInfoEntity.class);
	}

}
