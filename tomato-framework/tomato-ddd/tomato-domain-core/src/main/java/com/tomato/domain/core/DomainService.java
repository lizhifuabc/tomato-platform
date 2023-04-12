package com.tomato.domain.core;

/**
 * Domain Service 领域服务：是指领域层的业务逻辑处理，一般是一组方法的集合，
 * 它们用于完成某个具体的业务逻辑处理，可以涉及到多个 Entity 和 Value Object。
 * Domain Service 与 Repository 不同，它们是两种不同的设计思路，Repository 负责数据访问，
 * 而 Domain Service 负责业务逻辑处理。
 * 例如：订单的创建、支付、取消等操作，都可以封装成一个 Domain Service。
 * @author lizhifu
 * @since 2023/4/12
 */
public interface DomainService {
}
