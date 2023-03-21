package com.tomato.goods.feign.factory;

import com.tomato.goods.feign.RemoteGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

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
        log.error("商品服务远程调用接口降级处理:{}", throwable.getMessage());
        return null;
    }
}
