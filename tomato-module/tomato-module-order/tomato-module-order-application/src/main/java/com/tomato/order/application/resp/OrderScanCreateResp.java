package com.tomato.order.application.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 扫描订单创建返回
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderScanCreateResp extends OrderCreateResp{
    /**
     * 扫描地址
     */
    private String scanUrl;
}
