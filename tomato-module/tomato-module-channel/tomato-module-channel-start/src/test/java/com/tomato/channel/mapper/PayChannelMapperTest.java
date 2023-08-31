package com.tomato.channel.mapper;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * PayChannelMapper
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@SpringBootTest
public class PayChannelMapperTest {
	@Resource
	private PayChannelMapper payChannelMapper;

	@Test
    public void test() {
		payChannelMapper.selectByRuleNo("1");
	}
}
