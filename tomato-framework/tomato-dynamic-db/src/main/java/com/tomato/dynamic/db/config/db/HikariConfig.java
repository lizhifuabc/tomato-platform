package com.tomato.dynamic.db.config.db;

import lombok.Data;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * HikariConfig 数据库配置
 *
 * @author lizhifu
 * @since 2024/4/1
 */
@Data
public class HikariConfig {
	private String catalog;
	private long connectionTimeout;
	private long validationTimeout;
	private long idleTimeout;
	private long leakDetectionThreshold;
	private long maxLifetime;
	private int maximumPoolSize;
	private int minimumIdle;
	private String username;
	private String password;
	private long initializationFailTimeout;
	private String connectionInitSql;
	private String connectionTestQuery;
	private String dataSourceClassName;
	private String dataSourceJndiName;
	private String driverClassName;
	private String exceptionOverrideClassName;
	private String jdbcUrl;
	private String poolName;
	private String schema;
	private String transactionIsolationName;
	private boolean isAutoCommit;
	private boolean isReadOnly;
	private boolean isIsolateInternalQueries;
	private boolean isRegisterMbeans;
	private boolean isAllowPoolSuspension;
	private DataSource dataSource;
	private Properties dataSourceProperties;
	private Object metricRegistry;
	private Object healthCheckRegistry;
	private Properties healthCheckProperties;
	private long keepaliveTime;
	private boolean sealed;
}
