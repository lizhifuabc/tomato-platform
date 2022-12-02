package com.tomato.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体对象
 * id 字段 Long 类型，单表自增，自增长度为 1
 * version 为版本号 默认为 0
 * create_time 字段 datetime 类型，默认值 current_timestamp
 * update_time 字段 datetime 类型，默认值 current_timestamp, on update current_timestamp
 *
 * @author lizhifu
 * @date 2022/11/24
 */
public abstract class BaseEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 版本号
     */
    private Integer version;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
