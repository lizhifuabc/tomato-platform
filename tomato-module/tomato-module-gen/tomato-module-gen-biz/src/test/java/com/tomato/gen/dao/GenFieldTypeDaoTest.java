package com.tomato.gen.dao;

import com.tomato.gen.domain.entity.GenFieldTypeEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * GenFieldTypeDao
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@SpringBootTest
public class GenFieldTypeDaoTest {
    @Resource
    GenFieldTypeDao genFieldTypeDao;

    @Test
    public void selectAll(){
        genFieldTypeDao.selectAll().forEach(genFieldTypeEntity -> {
            System.out.println(genFieldTypeEntity.getCreateTime());
        });
    }
}