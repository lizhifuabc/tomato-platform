package com.tomato.channel;

import jakarta.annotation.Resource;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

/**
 * Channel
 *
 * @author lizhifu
 * @since 2023/8/25
 */
@SpringBootTest
public class ChannelRedisTest {
    public static final String ROUTE_ALARM = "route:alarm";
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void channel(){
        String channelNo = String.valueOf(System.currentTimeMillis());
        String payType = "wx";
        String sign = payType+":"+channelNo;

        // 存储统计的渠道
        stringRedisTemplate.opsForSet().add(ROUTE_ALARM,sign);

        // 存储指定渠道请求的时间戳（秒），同一秒的数据会覆盖存储
        long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        String key = ROUTE_ALARM + ":time:"+sign;
        stringRedisTemplate.opsForZSet().add(key, String.valueOf(second), second);

        // 模拟请求结果
        String key2 = ROUTE_ALARM + ":result:"+sign+":"+second;
        stringRedisTemplate.opsForHash().put(key2,"success","10");
        stringRedisTemplate.opsForHash().put(key2,"fail","12");

        // 根据key获取数据
        Set<String> members = stringRedisTemplate.opsForSet().members(ROUTE_ALARM);
        System.out.println(ROUTE_ALARM+">>>"+members);
        assert members != null;
        members.forEach(item->{
            String _key1 = ROUTE_ALARM + ":time:"+item;
            Set<String> range = stringRedisTemplate.opsForZSet().range(_key1, 0, -1);
            System.out.println(_key1+">>>"+range);
            assert range != null;
            range.forEach(time->{
                String _key2 = ROUTE_ALARM + ":result:"+item+":"+time;
                System.out.println(_key2+">>>"+stringRedisTemplate.opsForHash().entries(_key2));
            });
        });
    }
}
