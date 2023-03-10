package com.tomato.book.redis.structure;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * StringStructure 测试
 *
 * @author lizhifu
 * @since 2023/2/27
 */
@SpringBootTest
public class StringStructureTest {
    @Resource
    private StringStructure stringStructure;
    @Test
    public void test(){
        stringStructure.set("name","lizhifu");
        stringStructure.incr("name");
    }
}
