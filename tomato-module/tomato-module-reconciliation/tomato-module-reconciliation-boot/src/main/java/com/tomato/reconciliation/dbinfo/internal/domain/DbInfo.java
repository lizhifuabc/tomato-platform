package com.tomato.reconciliation.dbinfo.internal.domain;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jmolecules.ddd.types.Entity;

/**
 * 对账数据源信息
 *
 * @author lizhifu
 * @since 2023/5/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DbInfo extends BaseEntity {
    private String dbName;
    private String driver;
    private String url;
    private String username;
    private String password;
}
