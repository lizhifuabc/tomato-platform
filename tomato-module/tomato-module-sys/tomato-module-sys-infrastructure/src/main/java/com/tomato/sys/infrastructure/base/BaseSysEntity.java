package com.tomato.sys.infrastructure.base;

import com.tomato.jpa.domain.entity.JpaBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 * 权限实体基础
 *
 * @author lizhifu
 * @since 2023/4/9
 */
@MappedSuperclass//实体继承映射
public abstract class BaseSysEntity extends JpaBaseEntity {
    @Column(name = "create_by", length = 64)
    @CreatedBy
    @Comment("创建人")
    private String createBy;

    @Column(name = "update_by", length = 64)
    @LastModifiedBy
    @Comment("更新人")
    private String updateBy;

    @Column(name = "remark", length = 500)
    @Comment("备注")
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
