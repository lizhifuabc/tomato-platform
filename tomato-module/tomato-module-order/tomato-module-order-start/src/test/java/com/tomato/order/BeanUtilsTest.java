package com.tomato.order;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.infrastructure.dataobject.OrderInfoDO;
import org.springframework.beans.BeanUtils;

/**
 * BeanUtils
 *
 * @author lizhifu
 * @since 2023/8/2
 */
public class BeanUtilsTest {
    public static void main(String[] args) {
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();

        OrderInfoDO orderInfoDO = new OrderInfoDO();
        orderInfoDO.setChannelOrderNo("123");

        BeanUtils.copyProperties(orderInfoDO,orderInfoEntity);

        System.out.println(orderInfoEntity.getChannelOrderNo());
    }
}
