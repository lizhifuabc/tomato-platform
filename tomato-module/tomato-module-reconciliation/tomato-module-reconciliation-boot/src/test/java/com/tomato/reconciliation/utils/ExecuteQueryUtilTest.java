package com.tomato.reconciliation.utils;

import com.tomato.reconciliation.dbinfo.internal.domain.DbInfo;
import com.tomato.reconciliation.dbinfo.internal.mapper.DbInfoMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * ExecuteQueryUtil
 *
 * @author lizhifu
 * @since 2023/5/27
 */
@SpringBootTest
public class ExecuteQueryUtilTest {

	@Resource
	DbInfoMapper dbInfoMapper;

	@Test
	public void test() {
		Optional<DbInfo> dbInfo = dbInfoMapper.selectByPrimaryKey(1L);

		System.out.println(ExecuteQueryUtil.query(dbInfo.get(), "select * from t_db_info"));
	}

}
