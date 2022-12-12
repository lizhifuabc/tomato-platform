package com.tomato.order.domain.resp;

import com.tomato.jackson.datamask.DataMask;
import com.tomato.jackson.datamask.DataMaskEnum;
import lombok.Builder;
import lombok.Data;

/**
 * 订单查询
 *
 * @author lizhifu
 * @date 2022/12/12
 */
@Data
@Builder
public class OrderQueryResp {
    /**
     * 商户名称
     */
    @DataMask(function = DataMaskEnum.NAME)
    private String merchantName;
}
