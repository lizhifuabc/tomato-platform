package com.tomato.seckill.service;

import com.tomato.goods.feign.factory.RemoteGoodsFallbackFactory;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * RemoteGoodsFallbackFactory
 *
 * @author lizhifu
 * @since 2023/3/21
 */
@SpringBootTest
public class TestRemoteGoodsFallbackFactory {

	@Resource
	RemoteGoodsFallbackFactory remoteGoodsFallbackFactory;

	@Test
	public void test() {
		remoteGoodsFallbackFactory.create(null);
	}

}
