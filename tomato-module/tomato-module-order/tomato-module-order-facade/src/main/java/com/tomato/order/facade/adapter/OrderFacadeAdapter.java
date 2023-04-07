package com.tomato.order.facade.adapter;

import com.tomato.order.client.dto.OrderQueryDTO;
import com.tomato.order.client.dto.OrderQueryResultDTO;
import com.tomato.order.domain.domain.OrderQueryDomain;
import com.tomato.order.domain.domain.OrderQueryResultDomain;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/4/7
 */
@Service
public class OrderFacadeAdapter {
    /**
     * domain -> dto
     * @param domain
     * @return
     */
    public OrderQueryResultDTO convertQuery(OrderQueryResultDomain domain) {
        return null;
    }
    /**
     * dto -> domain
     * @param orderQueryDTOn
     * @return
     */
    public OrderQueryDomain convertQuery(OrderQueryDTO orderQueryDTOn) {
        return null;
    }
}
