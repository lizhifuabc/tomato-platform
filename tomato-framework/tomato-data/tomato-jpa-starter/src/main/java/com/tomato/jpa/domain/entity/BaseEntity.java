package com.tomato.jpa.domain.entity;

import com.tomato.common.entity.AbstractEntity;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 实体继承映射类基础,保存实体的通用属性
 *
 * @author lizhifu
 * @since  2022/11/25
 */
@MappedSuperclass//实体继承映射
// AuditingEntityListener会自动检查实体类中是否定义了@CreatedBy、
// @CreatedDate、@LastModifiedBy、@LastModifiedDate等注解，如果存在这些注解，则自动设置对应的元数据信息。
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseEntity extends AbstractEntity {
    /**
     * 创建时间
     */
    @Column(name = "create_time",nullable = false,updatable = false,columnDefinition="datetime not null default current_timestamp comment '创建时间'")
    @CreatedDate
    private LocalDateTime createTime = LocalDateTime.now();
    /**
     * 最后更新时间
     */
    @Column(name = "update_time",nullable = false,columnDefinition="datetime not null default current_timestamp on update current_timestamp comment '更新时间'")
    @LastModifiedDate
    private LocalDateTime updateTime = LocalDateTime.now();
    /**
     * 版本号
     */
    @Column(name = "version",nullable = false,columnDefinition="int default 0 not null comment '乐观锁'",insertable = false)
    @Version
    private Integer version = 0;

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
}
