package com.tomato.order;

import com.tomato.order.properties.SnowProperties;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SnowProperties
 *
 * @author lizhifu
 * @since 2023/1/3
 */
@SpringBootTest
public class SnowPropertiesTest {
    @Resource
    SnowProperties snowProperties;

    @Test
    public void test(){
        System.out.println(snowProperties.getWorkerId());
    }
}
