package com.tomato.sys.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

/**
 * 多租户
 *
 * @author lizhifu
 */
@Schema(title = "多租户")
@Entity
@Table(name = "t_sys_tenant")
@Cacheable
public class SysTenant extends BaseSysEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tenant_id")
	@Comment("租户ID")
	private Long tenantId;

	@Schema(name = "数据库用户名")
	@Column(name = "username", length = 100)
	private String username;

	@Schema(name = "数据库密码")
	@Column(name = "password", length = 100)
	private String password;

	@Schema(name = "数据库连接")
	@Column(name = "url", length = 1000)
	private String url;

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
