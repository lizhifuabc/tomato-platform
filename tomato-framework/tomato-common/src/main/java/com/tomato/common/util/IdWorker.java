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
 * <p>改良自雪花算法的分布式ID生成器</p>
 * 1位符号位(0) - 10位workerId - 41位timestamp - 12位计数ID
 * <p></p>
 * <p>原始雪花算法的分布式ID生成器</p>
 * 1位符号位(0) - 41位timestamp - 10位workerId - 12位计数ID
 * <p></p>
 * <p>雪花算法的几个缺点</p>
 * <p>1.时钟敏感：解决策略是记录上一次的时间戳，若发现当前时间戳小于记录值(意味着出现了时钟回拨)，则拒绝服务， 等待时间戳追上记录值。</p>
 * <p>2.突发性能有上限：因为算法的时间戳单位是毫秒，而分配给序列号的位长度为12，即每毫秒4096个序列空间，准确的描述应该是4096/ms。若当前时间戳的序列空间已耗尽，会自旋等待下一个时间戳。</p>
 * <p></p>
 * <p>Seata改进</p>
 * <p>1.解除与操作系统时间戳的时刻绑定，生成器只在初始化时获取了系统当前的时间戳，作为初始时间戳， 但之后就不再与系统时间戳保持同步了。</p>
 * <p>2.之后的递增，只由序列号的递增来驱动。比如序列号当前值是4095，下一个请求进来， 序列号+1溢出12位空间，序列号重新归零，而溢出的进位则加到时间戳上，从而让时间戳+1。</p>
 * <p>3.时间戳和序列号实际可视为一个整体了。</p>
 * @author funkye
 * @author selfishlover
 */
public class IdWorker {

    /**
     * Start time cut (2023-08-08 00:00:00)
     */
    private final long twepoch = 1691424000000L;

    /**
     * The number of bits occupied by workerId
     */
    private final int workerIdBits = 10;

    /**
     * The number of bits occupied by timestamp
     */
    private final int timestampBits = 41;

    /**
     * The number of bits occupied by sequence
     */
    private final int sequenceBits = 12;

    /**
     * Maximum supported machine id, the result is 1023
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
     * instantiate an IdWorker using given workerId
     * @param workerId if null, then will auto assign one
     */
    public IdWorker(Long workerId) {
        initTimestampAndSequence();
        initWorkerId(workerId);
    }

    /**
     * init first timestamp and sequence immediately
     */
    private void initTimestampAndSequence() {
        long timestamp = getNewestTimestamp();
        long timestampWithSequence = timestamp << sequenceBits;
        this.timestampAndSequence = new AtomicLong(timestampWithSequence);
    }

    /**
     * init workerId
     * @param workerId if null, then auto generate one
     */
    private void initWorkerId(Long workerId) {
        if (workerId == null) {
            workerId = generateWorkerId();
        }
        if (workerId > maxWorkerId || workerId < 0) {
            String message = String.format("worker Id can't be greater than %d or less than 0", maxWorkerId);
            throw new IllegalArgumentException(message);
        }
        this.workerId = workerId << (timestampBits + sequenceBits);
    }

    /**
     * get next UUID(base on snowflake algorithm), which look like:
     * 0 0000000000 00000000000000000000000000000000000000000 000000000000
     * highest 1 bit: always 0
     * next   10 bit: workerId
     * next   41 bit: timestamp
     * lowest 12 bit: sequence
     * @return UUID
     */
    public long nextId() {
        waitIfNecessary();
        long next = timestampAndSequence.incrementAndGet();
        long timestampWithSequence = next & timestampAndSequenceMask;
        return workerId | timestampWithSequence;
    }

    /**
     * 下一个id，字符串形式
     * @return UUID
     */
    public String nextIdStr() {
        return String.valueOf(nextId());
    }

    /**
     * block current thread if the QPS of acquiring UUID is too high
     * that current sequence space is exhausted
     */
    private void waitIfNecessary() {
        long currentWithSequence = timestampAndSequence.get();
        long current = currentWithSequence >>> sequenceBits;
        long newest = getNewestTimestamp();
        if (current >= newest) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignore) {
                // don't care
            }
        }
    }

    /**
     * get newest timestamp relative to twepoch
     */
    private long getNewestTimestamp() {
        return System.currentTimeMillis() - twepoch;
    }

    /**
     * auto generate workerId, try using mac first, if failed, then randomly generate one
     * @return workerId
     */
    private long generateWorkerId() {
        try {
            return generateWorkerIdBaseOnMac();
        } catch (Exception e) {
            return generateRandomWorkerId();
        }
    }

    /**
     * use lowest 10 bit of available MAC as workerId
     * @return workerId
     * @throws Exception when there is no available mac found
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
        throw new RuntimeException("no available mac found");
    }

    /**
     * randomly generate one as workerId
     * @return workerId
     */
    private long generateRandomWorkerId() {
        return new Random().nextInt(maxWorkerId + 1);
    }
}
