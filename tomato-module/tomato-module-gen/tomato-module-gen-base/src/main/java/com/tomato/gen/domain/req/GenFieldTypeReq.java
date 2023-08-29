package com.tomato.gen.domain.req;

import lombok.Data;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 表：t_gen_field_type 字段类型管理 req
 *
 * @author tomato
 * @since 2023-03-30T11:12:25.339646
 */
@Data
@Schema(description = "字段类型管理")
public class GenFieldTypeReq {

	/**
	 * 字段类型 data_type
	 */
	@NotBlank(message = "字段类型不能为空")
	@Schema(description = "字段类型")
	private String dataType;

	/**
	 * 属性类型 attr_type
	 */
	@NotBlank(message = "属性类型不能为空")
	@Schema(description = "属性类型")
	private String attrType;

	/**
	 * 属性包名 package_name
	 */
	@Schema(description = "属性包名")
	private String packageName;

	/**
	 * 主键 id
	 */
	@NotNull(message = "主键不能为空")
	@Schema(description = "主键")
	private Long id;

	/**
	 * 乐观锁 version
	 */
	@NotNull(message = "乐观锁不能为空")
	@Schema(description = "乐观锁")
	private Integer version;

	/**
	 * 更新时间 update_time
	 */
	@NotNull(message = "更新时间不能为空")
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 创建时间 create_time
	 */
	@NotNull(message = "创建时间不能为空")
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

}
