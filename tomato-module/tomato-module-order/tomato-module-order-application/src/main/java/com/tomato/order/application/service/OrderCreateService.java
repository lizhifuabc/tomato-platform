package com.tomato.order.application.service;

import com.tomato.order.application.req.OrderCreateReq;
import com.tomato.order.application.resp.OrderScanCreateResp;
import com.tomato.order.application.util.HmacUtil;
import com.tomato.order.domain.constants.OrderStatusEnum;
import com.tomato.order.domain.domain.entity.MerchantEntity;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.domain.repository.OrderInfoRepository;
import org.springframework.stereotype.Service;

/**
 * 订单新建
 *
 * @author lizhifu
 * @since  2022/12/1
 */
@Service
public class OrderCreateService {
    private final OrderInfoRepository orderInfoRepository;
    private final MerchantService merchantService;
    public OrderCreateService(OrderInfoRepository orderInfoRepository, MerchantService merchantService) {
        this.orderInfoRepository = orderInfoRepository;
        this.merchantService = merchantService;
    }

    /**
     * 订单新建
     * @param orderCreateReq 订单新建
     */
    public OrderScanCreateResp createScanOrder(OrderCreateReq orderCreateReq,String clientIp) {
        MerchantEntity query = MerchantEntity.builder()
                .merchantNo(orderCreateReq.getMerchantNo())
                .payType(orderCreateReq.getPayType())
                .build();
        MerchantEntity merchantEntity = merchantService.merchant(query);
        OrderInfoEntity orderInfoEntity = OrderInfoEntity.builder()
                .orderStatus(OrderStatusEnum.DEAL.getValue())
                .noticeWeb(orderCreateReq.getNoticeWeb())
                .noticeSys(orderCreateReq.getNoticeSys())
                .payType(orderCreateReq.getPayType())
                .merchantEntity(merchantEntity)
                .requestAmount(orderCreateReq.getRequestAmount())
                .merchantOrderNo(orderCreateReq.getMerchantOrderNo())
                .merchantNo(orderCreateReq.getMerchantNo())
                .extParam(orderCreateReq.getExtParam())
                .clientIp(clientIp)
                .hmac(orderCreateReq.getHmac())
                .orderNo(System.currentTimeMillis()+"")
                .build();
        // 校验 hmac
        orderInfoEntity.checkHmac(HmacUtil.hmac(orderCreateReq,merchantEntity.getMerchantKey()));
        orderInfoEntity.init();
        orderInfoRepository.createOrder(orderInfoEntity);

        OrderScanCreateResp orderScanCreateResp = new OrderScanCreateResp();
        return orderScanCreateResp;
    }
}
