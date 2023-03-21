package com.tomato.goods.controller;

import com.tomato.domain.resp.SingleResp;
import com.tomato.goods.dao.GoodsInfoDao;
import com.tomato.goods.domain.entity.GoodsInfoEntity;
import com.tomato.goods.domain.resp.GoodsInfoResp;
import com.tomato.web.common.BaseController;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品查询控制器
 *
 * @author lizhifu
 * @since 2023/3/20
 */
@RestController
public class GoodsInfoQueryController extends BaseController {
    private final GoodsInfoDao goodsInfoDao;

    public GoodsInfoQueryController(GoodsInfoDao goodsInfoDao) {
        this.goodsInfoDao = goodsInfoDao;
    }

    /**
     * 查询商品信息
     * @param id 商品id
     */
    @GetMapping("/goods/query/{id}")
    public SingleResp<GoodsInfoResp> queryGoodsInfo(@PathVariable("id") @NotNull Long id){
        GoodsInfoEntity goodsInfo =  goodsInfoDao.selectById(id);
        return SingleResp.of(copy(goodsInfo, GoodsInfoResp.class));
    }
}
