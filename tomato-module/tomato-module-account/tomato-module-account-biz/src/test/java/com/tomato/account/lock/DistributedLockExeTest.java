package com.tomato.account.lock;

import com.tomato.lock.core.exe.DistributedLockExe;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * DistributedLockExe
 *
 * @author lizhifu
 * @since 2023/1/17
 */
@SpringBootTest
public class DistributedLockExeTest {
    @Resource
    DistributedLockExe distributedLockExe;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void test(){
        for (int i = 0; i < 10; i++) {
            System.out.println(distributedLockExe.lock("test", 1000, 1000));
        }
//        System.out.println(distributedLockExe.lock("test", 1000, 1000));
//        System.out.println(stringRedisTemplate.opsForValue().get("test"));
//        System.out.println(distributedLockExe.unLock("test", null));
//        System.out.println(stringRedisTemplate.opsForValue().get("test"));
    }
}
