package com.tomato.order.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

/**
 * 分库
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Mapper
public interface ShardingDbMapper {

	/**
	 * 根据商户号查询分库
	 * @param merchantNoSpilt 商户号
	 * @return 分库
	 */
	@Select("select sharding_db from t_order_sharding_db where merchant_no_spilt = #{merchantNoSpilt}")
	Optional<String> selectShardingDb(@Param("merchantNoSpilt") String merchantNoSpilt);

}
