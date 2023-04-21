package com.tomato.goods.controller;

import com.tomato.common.resp.Resp;
import com.tomato.common.resp.Resp;
import com.tomato.goods.dao.GoodsInfoDao;
import com.tomato.goods.domain.entity.GoodsInfoEntity;
import com.tomato.goods.domain.resp.GoodsInfoResp;
import com.tomato.web.common.BaseController;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Resp<GoodsInfoResp> queryGoodsInfo(@PathVariable("id") @NotNull Long id) {
        GoodsInfoEntity goodsInfo =  goodsInfoDao.selectById(id);
        return Resp.of(copy(goodsInfo, GoodsInfoResp.class));
    }

    /**
     * 批量查询商品
     * @param ids 商品id
     * @return
     */
    @PostMapping("/goods/query/list")
     public Resp<List<GoodsInfoResp>> queryGoodsInfoList(@RequestBody @Size(max = 15) @NotNull List<Long> ids) {
         List<GoodsInfoEntity> goodsInfo =  goodsInfoDao.selectBatchByIds(ids);
        return Resp.of(copyList(goodsInfo, GoodsInfoResp.class));
     }
}
