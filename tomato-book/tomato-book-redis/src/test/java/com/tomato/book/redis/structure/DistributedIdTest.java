package com.tomato.book.redis.structure;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * DistributedId
 *
 * @author lizhifu
 * @since 2023/2/27
 */
@SpringBootTest
public class DistributedIdTest {
    @Resource
    DistributedId distributedId;
    @Test
    public void test(){
        String key = "test";
        int increment = 1;
        System.out.println(distributedId.generateId(key));
        System.out.println(distributedId.generateId(key, increment));
        System.out.println(distributedId.getId(key));
    }
}

