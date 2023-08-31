package com.tomato.channel.mapper;

import com.tomato.channel.domain.PayChannel;
import com.tomato.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支付渠道
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Mapper
public interface PayChannelMapper extends BaseMapper<PayChannel, Long> {
	/**
	 * 根据路由编号</u>
	 * @param ruleNo 跟由编号
	 * @return 渠道
	 */
	List<PayChannel> selectByRuleNo(@Param("ruleNo") String ruleNo);

}
