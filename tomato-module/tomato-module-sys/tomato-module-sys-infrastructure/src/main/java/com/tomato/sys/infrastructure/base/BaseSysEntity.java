package com.tomato.sys.infrastructure.base;

import com.tomato.jpa.domain.entity.JpaBaseEntity;
import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 * 权限实体基础
 *
 * @author lizhifu
 * @since 2023/4/9
 */
public abstract class BaseSysEntity extends JpaBaseEntity {
    @Column(name = "create_by")
    @CreatedBy
    private String createBy;
    @Column(name = "update_by")
    @LastModifiedBy
    private String updateBy;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
