package com.tomato.dynamic.db.config;

import com.tomato.dynamic.db.holder.DynamicDataSourceHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 设置/切换数据源，决定当前线程使用哪个数据源
	 * @return 数据源名称
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDynamicDataSourceKey();
	}

}
