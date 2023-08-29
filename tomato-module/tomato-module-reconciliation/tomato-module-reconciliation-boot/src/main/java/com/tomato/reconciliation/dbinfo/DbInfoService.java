package com.tomato.reconciliation.dbinfo;

import com.tomato.reconciliation.dbinfo.internal.domain.DbInfo;

import java.sql.*;

/**
 * 对账数据源信息
 *
 * @author lizhifu
 * @since 2023/5/27
 */
public class DbInfoService {

	private static final String TEST_CONNECT_SQL = "show databases";

	public void testConnection(DbInfo dbInfo) throws Exception {
		try {
			Class.forName(dbInfo.getDriver());
		}
		catch (ClassNotFoundException e) {
			throw new Exception("注册驱动失败");
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbInfo.getUrl(), dbInfo.getUsername(), dbInfo.getPassword());
			Statement stat = conn.createStatement();
			ResultSet re = stat.executeQuery(TEST_CONNECT_SQL);
			int i = 0;
			while (re.next()) {
				i++;
			}
			re.close();
			stat.close();
			conn.close();
			if (i == 0) {
				throw new Exception("该连接下没有库");
			}
		}
		catch (SQLException e) {
			throw new Exception("连接数据库失败");
		}
	}

}
