package com.tomato.goods.domain.resp;

import lombok.Data;

/**
 * 商品基本信息返回
 *
 * @author lizhifu
 * @since 2023/3/21
 */
@Data
public class GoodsInfoResp {
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 主键
     */
    private Long id;
}
