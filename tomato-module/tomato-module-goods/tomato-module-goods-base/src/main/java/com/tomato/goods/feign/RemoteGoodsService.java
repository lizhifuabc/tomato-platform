package com.tomato.goods.feign;

import com.tomato.common.domain.resp.Resp;
import com.tomato.goods.domain.resp.GoodsInfoResp;
import com.tomato.goods.feign.factory.RemoteGoodsFallbackFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 商品服务远程调用接口
 *
 * @author lizhifu
 * @since 2023/3/21
 */
@FeignClient(contextId = "remoteClientDetailsService", value = "tomato-module-goods-biz",
		fallbackFactory = RemoteGoodsFallbackFactory.class)
public interface RemoteGoodsService {

	/**
	 * 查询商品信息
	 * @param id 商品id
	 * @return 商品信息
	 */
	@GetMapping("/goods/query/{id}")
	Resp<GoodsInfoResp> queryGoodsInfo(@PathVariable("id") @NotNull Long id);

	/**
	 * 批量查询商品
	 * @param ids 商品id
	 * @return
	 */
	@PostMapping("/goods/query/list")
	Resp<GoodsInfoResp> queryGoodsInfoList(@RequestBody @Size(max = 15) @NotNull List<Long> ids);

}
