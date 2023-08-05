package com.tomato.order.infrastructure.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分库
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShardingDbDO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 商户号
     */
    private String merchantNoSpilt;
    /**
     * 分库
     */
    private String shardingDb;
}
