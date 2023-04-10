package com.tomato.sys.infrastructure.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.tomato.sys.infrastructure.base.BaseSysEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统角色 TODO 缓存
 * 使用 @Data 注解来生成 JPA 实体类的 getter、setter、equals、hashCode 和 toString 方法是有问题的，
 * 可能会导致严重的性能问题。这是因为 @Data 注解会自动生成 equals 和 hashCode 方法，
 * 这些方法会递归调用实体类的所有属性，包括关联实体类的属性，从而导致关联实体类的数据也被加载到内存中，
 * 可能会导致大量的内存消耗和性能问题。
 * 相反，推荐使用 @Getter、@Setter、@EqualsAndHashCode 和 @ToString
 * 注解来生成 JPA 实体类的 getter、setter、equals、hashCode 和 toString 方法，
 * 这些注解只会生成指定属性的方法，而不会递归调用关联实体类的属性。
 * @author lizhifu
 */
@Entity
@Table(name = "sys_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"role_code"})},
        indexes = {@Index(name = "sys_role_rcd_idx", columnList = "role_code")})
public class SysRole extends BaseSysEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "role_id")
    @Comment("角色ID")
    private Long roleId;

    @Column(name = "role_code", length = 128, unique = true, nullable = false)
    @Comment("角色编码")
    private String roleCode;

    @Column(name = "role_name", length = 128, nullable = false)
    @Comment("角色名称")
    private String roleName;

    @Column(name = "role_status", nullable = false)
    @Comment("角色状态")
    private Boolean roleStatus = Boolean.TRUE;
    /**
     * 用户 - 角色关系定义:
     * primary key (role_id, permission_id)
     * 覆盖索引：如果联合索引包含了所有需要查询的列，那么即使没有使用联合索引的第一个列，MySQL 仍然可以使用该索引来优化查询，并从索引中获取需要的数据，这种情况称为 "Covering Index"。
     * 存在索引覆盖扫描的情况：如果查询中只涉及到联合索引的一部分列，而剩余的列已经被索引覆盖扫描到了，那么即使没有使用联合索引的第一个列，MySQL 仍然可以使用该索引来优化查询。
     * 最左前缀匹配：如果查询中只涉及到联合索引的前面几列，并且这些列组成了联合索引的最左前缀，那么即使没有使用联合索引的第一个列，MySQL 仍然可以使用该索引来优化查询。
     * 需要注意的是，虽然在某些情况下可以不使用联合索引的第一个列来优化查询，但是在使用索引时，还是应该尽量遵循从左到右的顺序使用索引列，这样可以更好地利用索引来提高查询性能。
     */

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "sys_role_permission",
            joinColumns = {@JoinColumn(name = "role_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = {@JoinColumn(name = "permission_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "permission_id"})},
            indexes = {@Index(name = "sys_role_permission_rid_idx", columnList = "role_id"), @Index(name = "sys_role_permission_pid_idx", columnList = "permission_id")})
    private Set<SysPermission> permissions = new HashSet<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysRole sysRole = (SysRole) o;
        return Objects.equal(roleId, sysRole.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roleId);
    }
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("roleId", roleId)
                .add("roleCode", roleCode)
                .add("roleName", roleName)
                .toString();
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Boolean roleStatus) {
        this.roleStatus = roleStatus;
    }

    public Set<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<SysPermission> permissions) {
        this.permissions = permissions;
    }
}
