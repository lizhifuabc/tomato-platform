package com.tomato.redis.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/4/2
 */
public class RedisLock implements Lock {
    /**
     * 可重入锁,一个线程可以多次获取锁
     */
    private final ReentrantLock localLock = new ReentrantLock();

    @Override
    public void lock() {
        this.localLock.lock();
        while (true) {
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {

            }
            catch (Exception e){
                this.localLock.unlock();
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
