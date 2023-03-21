package com.tomato.goods.feign;

import com.tomato.domain.resp.SingleResp;
import com.tomato.goods.domain.resp.GoodsInfoResp;
import com.tomato.goods.feign.factory.RemoteGoodsFallbackFactory;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 商品服务远程调用接口
 *
 * @author lizhifu
 * @since 2023/3/21
 */
@FeignClient(contextId = "remoteClientDetailsService", value = "tomato-module-goods-biz" , fallbackFactory = RemoteGoodsFallbackFactory.class)
public interface RemoteGoodsService {
    /**
     * 查询商品信息
     * @param id 商品id
     * @return 商品信息
     */
    @GetMapping("/goods/query/{id}")
    SingleResp<GoodsInfoResp> queryGoodsInfo(@PathVariable("id") @NotNull Long id);
}
