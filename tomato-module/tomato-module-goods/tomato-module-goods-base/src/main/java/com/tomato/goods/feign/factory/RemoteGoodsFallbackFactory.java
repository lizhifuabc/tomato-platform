package com.tomato.goods.feign.factory;

import com.tomato.domain.resp.MultiResp;
import com.tomato.domain.resp.SingleResp;
import com.tomato.goods.domain.resp.GoodsInfoResp;
import com.tomato.goods.feign.RemoteGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品服务远程调用接口降级处理
 *
 * @author lizhifu
 * @since 2023/3/21
 */
@Component
@Slf4j
public class RemoteGoodsFallbackFactory implements FallbackFactory<RemoteGoodsService> {
    @Override
    public RemoteGoodsService create(Throwable throwable) {
        return new RemoteGoodsService() {
            @Override
            public SingleResp<GoodsInfoResp> queryGoodsInfo(Long id) {
                log.error("商品{}服务远程调用接口[queryGoodsInfo]降级处理", id, throwable);
                return SingleResp.buildFailure("商品服务远程调用接口[queryGoodsInfo]降级处理");
            }

            @Override
            public MultiResp<GoodsInfoResp> queryGoodsInfoList(List<Long> ids) {
                log.error("商品{}服务远程调用接口[queryGoodsInfoList]降级处理", ids, throwable);
                return MultiResp.buildFailure("商品服务远程调用接口[queryGoodsInfoList]降级处理");
            }
        };
    }
}
