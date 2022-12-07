package com.tomato.jpa.domain.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体继承映射类基础,保存实体的通用属性
 *
 * @author lizhifu
 * @date 2022/11/25
 */
@MappedSuperclass//实体继承映射
@EntityListeners(value = AuditingEntityListener.class)
public abstract class JpaBaseEntity implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 创建时间
     */
    @Column(name = "create_time",nullable = false,updatable = false)
    @CreatedDate
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    @Column(name = "update_time",nullable = false)
    @LastModifiedDate
    private LocalDateTime updateTime;
    /**
     * 版本号
     */
    @Column(name = "version",nullable = false,columnDefinition="int default 0",insertable = false)
    @Version
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
