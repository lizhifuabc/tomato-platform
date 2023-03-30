package com.tomato.gen.dao;

import com.tomato.gen.domain.resp.TableColumnResp;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * CodeGenDao
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@SpringBootTest
public class GenTableDaoTest {
    @Resource
    GenTableDao genTableDao;

    @Test
    public void test(){
        String tableName = "t_gen_field_type";
        // 测试表是否存在
        List<TableColumnResp> tableColumnResp = genTableDao.selectTableColumnByTableName(tableName);
        System.out.println(tableColumnResp);
        System.out.println(genTableDao.selectTableByTableName(tableName));
    }
}