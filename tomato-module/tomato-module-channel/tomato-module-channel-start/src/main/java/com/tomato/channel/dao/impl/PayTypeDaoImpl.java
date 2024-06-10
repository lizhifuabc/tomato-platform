package com.tomato.channel.dao.impl;

import com.tomato.channel.dao.PayTypeDao;
import com.tomato.channel.dataobject.PayType;
import com.tomato.mybatis.mp.dao.impl.MpBaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Dao 实现类
 *
 * @author lizhifu
 * @since 2024/6/10
 */
@Repository
public class PayTypeDaoImpl extends MpBaseDaoImpl<PayType, Long> implements PayTypeDao {

}
