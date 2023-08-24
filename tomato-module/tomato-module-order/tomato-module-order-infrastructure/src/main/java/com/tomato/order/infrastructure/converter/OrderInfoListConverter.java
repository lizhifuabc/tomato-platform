package com.tomato.order.infrastructure.converter;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.infrastructure.dataobject.OrderInfoDO;
import com.tomato.web.core.util.BeanUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * <p>订单转换器</p>
 *
 * @author lizhifu
 * @since 2023/8/24
 */
public class OrderInfoListConverter implements Converter<List<OrderInfoDO>, List<OrderInfoEntity>> {
    @Override
    public List<OrderInfoEntity> convert(@NotNull List<OrderInfoDO> source) {
        return BeanUtil.copyList(source, OrderInfoEntity.class);
    }
}
