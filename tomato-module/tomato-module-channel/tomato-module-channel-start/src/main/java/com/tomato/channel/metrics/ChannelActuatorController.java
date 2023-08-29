package com.tomato.channel.metrics;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 渠道指标监测
 *
 * @author lizhifu
 * @since 2023/8/26
 */
@RestController
@RequestMapping
@Slf4j
@Tags({ @Tag(name = "渠道指标监测"), })
public class ChannelActuatorController {

	/**
	 * 渠道指标监测
	 * @return 渠道指标监测
	 */
	@RequestMapping(value = "/channel/actuator")
	public String actuator() {
		log.info("渠道指标监测");
		return "渠道指标监测";
	}

}
