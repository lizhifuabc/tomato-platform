package com.tomato.pay;

import com.tomato.pay.domain.event.PayResultEvent;
import com.tomato.pay.infrastructure.mq.PayResultProduct;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * payResultProduct
 *
 * @author lizhifu
 * @since 2023/4/12
 */
@SpringBootTest
public class PayResultProductTest {

	@Resource
	PayResultProduct payResultProduct;

	@Test
	public void test() {
		PayResultEvent payResultEvent = new PayResultEvent();
		payResultEvent.setOrderNo("123");
		payResultProduct.send(payResultEvent);
	}

}
