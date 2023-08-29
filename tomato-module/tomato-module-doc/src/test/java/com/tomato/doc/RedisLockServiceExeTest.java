package com.tomato.doc;

import com.tomato.doc.redis.RedisLockService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * RedisLockRegistry
 *
 * @author lizhifu
 * @since 2023/3/27
 */
@SpringBootTest
public class RedisLockServiceExeTest {

	@Resource
	RedisLockService redisLockService;

	@Test
	public void test() throws InterruptedException {
		redisLockService.obtain("test").lock();
		Thread.sleep(10000);
		redisLockService.obtain("test").unlock();
	}

}
