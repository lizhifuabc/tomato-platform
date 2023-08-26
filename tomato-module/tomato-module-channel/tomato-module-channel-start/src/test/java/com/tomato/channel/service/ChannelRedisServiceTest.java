package com.tomato.channel.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Set;

/**
 * ChannelRedisService
 *
 * @author lizhifu
 * @since 2023/8/26
 */
@SpringBootTest
public class ChannelRedisServiceTest {
    @Resource
    private ChannelRedisService channelRedisService;

    @Test
    public void test() {
        String channelNo = String.valueOf(System.currentTimeMillis());
        channelNo = "1630000000000";
        String payType = "wx";

        for (int i = 0; i < 10; i++) {
            channelRedisService.addChannelAlarm(payType, channelNo);
            long request = channelRedisService.addChannelRequest(payType, channelNo);
            // 模拟随机请求时间
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException ignored) {
            }
            channelRedisService.addChannelResult(payType, channelNo, "success",request);
            channelRedisService.addChannelResult(payType, channelNo, "fail",request);
        }

        Set<String> channelAlarm = channelRedisService.getChannelAlarm();
        System.out.println("获取统计的渠道:" + channelAlarm);
        channelAlarm.forEach(item -> {
            System.out.println("===========获取渠道数据："+item+"===========");
            Set<String> channelRequest = channelRedisService.getChannelRequest(item);
            System.out.println("获取指定渠道请求的时间戳（秒）:" + channelRequest);
        });

        System.out.println("获取渠道请求结果:" + channelRedisService.getChannelResult(payType, channelNo));;
    }
}
