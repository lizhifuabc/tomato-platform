package com.tomato.order.domain.repository;

/**
 * Repository只负责Entity对象的存储和读取，而Repository的实现类完成数据库存储的细节。
 * 通过加入Repository接口，底层的数据库连接可以通过不同的实现类而替换。
 *
 * @author lizhifu
 * @since 2023/8/1
 */
public interface OrderInfoRepository {
}
