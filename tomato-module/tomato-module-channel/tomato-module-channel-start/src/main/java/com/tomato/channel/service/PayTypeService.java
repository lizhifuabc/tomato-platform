package com.tomato.channel.service;

import com.tomato.channel.dao.PayTypeDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Service 支付方式
 *
 * @author lizhifu
 * @since 2024/6/10
 */
@Service
public class PayTypeService {
	@Resource
	private PayTypeDao payTypeDao;
}
