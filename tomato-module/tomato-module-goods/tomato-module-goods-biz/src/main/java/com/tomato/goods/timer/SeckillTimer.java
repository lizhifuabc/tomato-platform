package com.tomato.goods.timer;

import com.tomato.goods.seckill.dao.SeckillActivityDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 缓存预热
 * <p> TODO 注册定时任务到分布式调度中心，用于预热商品数据</p>
 * @author lizhifu
 * @date 2022/12/10
 */
@Component
@Slf4j
public class SeckillTimer {
    private final SeckillActivityDao seckillActivityDao;

    public SeckillTimer(SeckillActivityDao seckillActivityDao) {
        this.seckillActivityDao = seckillActivityDao;
    }

    @Scheduled(fixedRate = 5000L)
    public void run() {
        List<Long> longList = seckillActivityDao.selectByTime(LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        log.info("执行缓存预热{}", longList);
    }
}
