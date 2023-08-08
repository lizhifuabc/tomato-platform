/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tomato.common.util;

import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * IdWorker:来源 <a href="https://github.com/seata/seata">seata</a>
 * <p>
 * 雪花算法：
 * 0(1位:恒为0)-(时间戳:41位)00000000000000000000000000000000000000000-(节点ID:10位)0000000000-(序列号12位)000000000000
 * <p>
 * seata改良版雪花算法：
 * 0(1位:恒为0)-(节点ID:10位)0000000000-(时间戳:41位)00000000000000000000000000000000000000000-(序列号12位)000000000000
 *
 * @author funkye
 * @author selfishlover
 */
public class IdWorker {

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     * 开始时间 (2023-08-08 00:00:00)
     */
    private final long twepoch = 1691424000000L;

    /**
     * workerId 占用的位数
     */
    private final int workerIdBits = 10;

    /**
     * 时间戳占用的位数
     */
    private final int timestampBits = 41;

    /**
     * 序列号占用的位数
     */
    private final int sequenceBits = 12;

    /**
     * 最大 workerId ID，结果是1023
     */
    private final int maxWorkerId = ~(-1 << workerIdBits);

    /**
     * business meaning: machine ID (0 ~ 1023)
     * actual layout in memory:
     * highest 1 bit: 0
     * middle 10 bit: workerId
     * lowest 53 bit: all 0
     */
    private long workerId;

    /**
     * timestamp and sequence mix in one Long
     * highest 11 bit: not used
     * middle  41 bit: timestamp
     * lowest  12 bit: sequence
     */
    private AtomicLong timestampAndSequence;

    /**
     * mask that help to extract timestamp and sequence from a long
     */
    private final long timestampAndSequenceMask = ~(-1L << (timestampBits + sequenceBits));

    /**
     * IdWorker 构造，使用给定的workerId
     * @param workerId 如果为null，则自动生成一个
     */
    public IdWorker(Long workerId) {
        initTimestampAndSequence();
        initWorkerId(workerId);
    }

    /**
     * init first timestamp and sequence immediately
     * 初始化时间戳和序列号
     */
    private void initTimestampAndSequence() {
        // 获取当前时间戳 - twepoch 时间戳
        long timestamp = getNewestTimestamp();
        // 时间戳左移12位，使其占用的位数为53位(41+12)
        long timestampWithSequence = timestamp << sequenceBits;
        this.timestampAndSequence = new AtomicLong(timestampWithSequence);
    }

    /**
     * 初始化workerId
     * @param workerId 如果为null，则自动生成一个
     */
    private void initWorkerId(Long workerId) {
        if (workerId == null) {
            // 自动生成workerId
            workerId = generateWorkerId();
        }
        // workerId不能大于1023或小于0
        if (workerId > maxWorkerId || workerId < 0) {
            String message = String.format("workerId 不能大于 %d 或小于0", maxWorkerId);
            throw new IllegalArgumentException(message);
        }
        // workerId左移52位，使其占用的位数为53位(41+12)
        this.workerId = workerId << (timestampBits + sequenceBits);
    }

    /**
     * Seata 的分布式 ID 生成器，不再依赖于时间戳，而是依赖于序列号
     * get next UUID(base on snowflake algorithm), which look like:
     * highest 1 bit: always 0
     * next   10 bit: workerId
     * next   41 bit: timestamp
     * lowest 12 bit: sequence
     * @return UUID
     */
    public long nextId() {
        waitIfNecessary();
        // 基于 timestampWithSequence 进行递增，即 +1 操作
        long next = timestampAndSequence.incrementAndGet();
        // 截取低 53 位，也就是 41 位的时间戳和 12 位的序列号
        long timestampWithSequence = next & timestampAndSequenceMask;
        // 高 11 位替换为前面说过的值在 [0,1023] 之间的 workerId
        return workerId | timestampWithSequence;
    }

    /**
     * 如果获取UUID的QPS过高，则阻塞当前线程
     * 当前序列空间已用尽
     * 1. 序列号只有 12 位，它的取值范围就是 [0,4095]
     * 2. 序列号就是生成到了 4096 导致溢出，序列号重新归 0，溢出的这一位加到时间戳上，让时间戳 +1
     * 3. 时间戳 +1 了，会导致一种“超前消费”的情况出现，导致时间戳和系统时间不一致
     * 4. 当前这个毫秒下，4096 个序列号不够用了，4096/ms，约 400w/s（理论值，就是发生了，崩溃的地方也不会是因为这个原因）
     */
    private void waitIfNecessary() {
        // 当前时间戳和序列号
        long currentWithSequence = timestampAndSequence.get();
        // 合并后的值右移 12 位，得到当前时间戳的值，因为前面时间戳占了 41 位，所以右移 12 位可以得到时间戳的值
        long current = currentWithSequence >>> sequenceBits;
        // 最新时间戳 - twepoch 时间戳
        long newest = getNewestTimestamp();
        // 当前时间戳小于最新时间戳，表示序列号用尽，需要阻塞当前线程
        if (current >= newest) {
            try {
                // 阻塞当前线程，直到时间戳更新
                Thread.sleep(5);
            } catch (InterruptedException ignore) {
                // don't care
            }
        }
    }

    /**
     * 获取相对于twepoch的最新时间戳
     * @return timestamp 当前时间戳 - twepoch 时间戳
     */
    private long getNewestTimestamp() {
        // 当前时间戳 - twepoch 时间戳
        return System.currentTimeMillis() - twepoch;
    }

    /**
     * 生成workerId，优先使用mac地址，如果失败，则随机生成一个
     * @return workerId
     */
    private long generateWorkerId() {
        try {
            // 从可用的mac地址中获取最低的10位作为workerId(节点ID)
            return generateWorkerIdBaseOnMac();
        } catch (Exception e) {
            // 如果失败，则随机生成一个
            return generateRandomWorkerId();
        }
    }

    /**
     * 从可用的mac地址中获取最低的10位作为workerId(节点ID)
     * @return workerId (0 ~ 1023)
     * @throws Exception 没有找到可用的mac地址
     */
    private long generateWorkerIdBaseOnMac() throws Exception {
        Enumeration<NetworkInterface> all = NetworkInterface.getNetworkInterfaces();
        while (all.hasMoreElements()) {
            NetworkInterface networkInterface = all.nextElement();
            boolean isLoopback = networkInterface.isLoopback();
            boolean isVirtual = networkInterface.isVirtual();
            if (isLoopback || isVirtual) {
                continue;
            }
            byte[] mac = networkInterface.getHardwareAddress();
            return ((mac[4] & 0B11) << 8) | (mac[5] & 0xFF);
        }
        throw new RuntimeException("没有找到可用的mac地址");
    }

    /**
     * 生成一个随机的workerId(节点ID)
     * @return workerId (0 ~ 1023)
     */
    private long generateRandomWorkerId() {
        return new Random().nextInt(maxWorkerId + 1);
    }
}
