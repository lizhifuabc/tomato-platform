package com.tomato.seckill.timer;

import com.tomato.seckill.dao.SeckillActivityDao;
import com.tomato.seckill.manager.SeckillGoodsCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 缓存预热
 * <p> TODO 注册定时任务到分布式调度中心，用于预热商品数据</p>
 * TODO 定时锁
 * @author lizhifu
 * @date 2022/12/10
 */
@Component
@Slf4j
public class SeckillTimer {
    private final SeckillActivityDao seckillActivityDao;
    private final SeckillGoodsCacheManager seckillGoodsCacheManager;
    public SeckillTimer(SeckillActivityDao seckillActivityDao, SeckillGoodsCacheManager seckillGoodsCacheManager) {
        this.seckillActivityDao = seckillActivityDao;
        this.seckillGoodsCacheManager = seckillGoodsCacheManager;
    }

    @Scheduled(fixedRate = 1000 * 60L)
    public void run() {
        // 提前一天预热 2022-12-19 00:00:00
        List<Long> longList = seckillActivityDao.selectByTime(LocalDateTime.now().plusDays(1).with(LocalTime.MIN), LocalDateTime.now().plusDays(1).with(LocalTime.MAX));
        log.info("执行缓存预热{}", longList);
        longList.forEach(id->{
            seckillGoodsCacheManager.cache(id);
        });
    }
}
