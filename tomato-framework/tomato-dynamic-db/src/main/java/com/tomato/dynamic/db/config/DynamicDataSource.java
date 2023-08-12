package com.tomato.dynamic.db.config;

import com.tomato.dynamic.db.holder.DynamicDataSourceHolder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * 动态数据源
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@AllArgsConstructor
@NoArgsConstructor
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 所有数据源
     */
    private Map<Object, Object> defineTargetDataSources;

    /**
     * 设置/切换数据源，决定当前线程使用哪个数据源
     * @return 数据源名称
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDynamicDataSourceKey();
    }

    public Map<Object, Object> getDefineTargetDataSources() {
        return defineTargetDataSources;
    }

    public void setDefineTargetDataSources(Map<Object, Object> defineTargetDataSources) {
        this.defineTargetDataSources = defineTargetDataSources;
    }
}
