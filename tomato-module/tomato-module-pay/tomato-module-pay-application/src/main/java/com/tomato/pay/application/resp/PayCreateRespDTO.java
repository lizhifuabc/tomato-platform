package com.tomato.pay.application.resp;

import lombok.Data;

/**
 * 支付结果返回
 *
 * @author lizhifu
 * @since 2023/4/12
 */
@Data
public class PayCreateRespDTO {
    /**
     * 支付返回信息
     * TODO 各种支付渠道返回的信息不一样，需要根据不同的支付渠道进行处理
     */
    private String body;
}
