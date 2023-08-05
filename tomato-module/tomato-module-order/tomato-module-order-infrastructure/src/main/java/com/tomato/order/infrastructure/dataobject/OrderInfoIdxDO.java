package com.tomato.order.infrastructure.dataobject;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 订单索引表
 * @author lizhifu
 * @since 2023/8/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_order_info_idx")
public class OrderInfoIdxDO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 商户订单号
     */
    private String merchantOrderNo;

    /**
     * 商户编号
     */
    private String merchantNo;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
