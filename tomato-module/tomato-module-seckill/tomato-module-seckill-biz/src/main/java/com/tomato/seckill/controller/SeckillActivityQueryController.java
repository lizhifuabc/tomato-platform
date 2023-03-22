package com.tomato.seckill.controller;

import com.tomato.domain.resp.MultiResp;
import com.tomato.seckill.dao.SeckillActivityDao;
import com.tomato.seckill.domain.entity.SeckillActivityEntity;
import com.tomato.seckill.domain.resp.SeckillActivityQueryResp;
import com.tomato.web.common.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 秒杀活动查询
 *
 * @author lizhifu
 * @since 2023/3/22
 */
@RestController
public class SeckillActivityQueryController extends BaseController {
    private final SeckillActivityDao seckillActivityDao;

    public SeckillActivityQueryController(SeckillActivityDao seckillActivityDao) {
        this.seckillActivityDao = seckillActivityDao;
    }

    /**
     * 获取秒杀活动列表:
     * 1. 活动开始时间小于当前时间
     * 2. 活动结束时间大于当前时间
     * TODO 优化：缓存？
     * @return 秒杀活动列表
     */
    @GetMapping("/seckill/activity/list")
    public MultiResp querySeckillActivityList(){
        // 获取秒杀活动列表
        LocalDateTime start = LocalDateTime.now();
        List<SeckillActivityEntity> entityList = seckillActivityDao.selectByStartTime(start);
        return MultiResp.of(copyList(entityList, SeckillActivityQueryResp.class));
    }
}
