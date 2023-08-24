package com.tomato.order.infrastructure.converter;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.infrastructure.dataobject.UpdateOrderStatusDO;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>订单状态更新转换器</p>
 *
 * @author lizhifu
 * @since 2023/8/24
 */
public class UpdateOrderStatusDOConverter implements Converter<OrderInfoEntity,UpdateOrderStatusDO> {
    @Override
    public UpdateOrderStatusDO convert(OrderInfoEntity orderInfoEntity) {
        return UpdateOrderStatusDO.builder()
                .orderStatus(orderInfoEntity.getOrderStatus())
                .currentVersion(orderInfoEntity.getVersion())
                .completeTime(orderInfoEntity.getCompleteTime())
                .orderNo(orderInfoEntity.getOrderNo())
                .expectOrderStatus(orderInfoEntity.getExpectOrderStatus())
                .accountStatus(orderInfoEntity.getAccountStatus())
                .noticeStatus(orderInfoEntity.getNoticeStatus())
                .build();
    }
}
