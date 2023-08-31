package com.tomato.channel.mapper;

import com.tomato.channel.domain.MerchantRouterRule;
import com.tomato.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商户绑定路由规则表，用于存储商户和路由规则之间的关联关系
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Mapper
public interface MerchantRouterRuleMapper extends BaseMapper<MerchantRouterRule, Long> {
	/**
	 * 根据商户和支付类型查询详情:状态为激活状态
	 * @param merchantNo 商户号
	 * @param payType 支付类型
	 * @return 商户绑定路由规则表
	 */
	public List<MerchantRouterRule> selectByMerchant(@Param("merchantNo") String merchantNo,
													 @Param("payType") String payType);
}
