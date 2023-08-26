package com.tomato.channel.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tomato.channel.constants.RedisConstants.CHANNEL_ALARM;

/**
 * 渠道 redis 服务
 *
 * @author lizhifu
 * @since 2023/8/26
 */
@Service
public class ChannelRedisService {
    private final StringRedisTemplate stringRedisTemplate;

    public ChannelRedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 存储统计的渠道
     * @param payType 支付类型
     * @param channelNo 渠道编号
     */
    public void addChannelAlarm(String payType,String channelNo){
        // 存储统计的渠道
        stringRedisTemplate.opsForSet().add(CHANNEL_ALARM,payType+":"+channelNo);
    }

    /**
     * 获取统计的渠道
     * @return 渠道编号
     */
    public Set<String> getChannelAlarm(){
        // 根据key获取数据
        return stringRedisTemplate.opsForSet().members(CHANNEL_ALARM);
    }
    /**
     * 存储指定渠道请求的时间戳（秒），同一秒的数据会覆盖存储
     * @param payType 支付类型
     * @param channelNo 渠道编号
     */
    public long addChannelRequest(String payType,String channelNo){
        long second = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        stringRedisTemplate.opsForZSet().add(channelTimeKey(payType,channelNo), String.valueOf(second), second);
        return second;
    }
    /**
     * 获取指定渠道请求的时间戳（秒）
     *
     * @param payType   支付类型
     * @param channelNo 渠道编号
     * @return 时间戳（秒）
     */
    public Set<String> getChannelRequest(String payType, String channelNo) {
        return stringRedisTemplate.opsForZSet().range(channelTimeKey(payType,channelNo), 0, -1);
    }
    /**
     * 获取指定渠道请求的时间戳（秒）
     *
     * @param sign 支付方式-渠道编号
     * @return 结果
     */
    public Set<String> getChannelRequest(String sign) {
        return stringRedisTemplate.opsForZSet().range(channelTimeKey(sign), 0, -1);
    }
    /**
     * 存储渠道请求结果
     * @param payType 支付类型
     * @param channelNo 渠道编号
      * @param second 时间戳（秒）
     * @param type 请求结果类型(success/fail)
     */
    public void addChannelResult(String payType,String channelNo,String type,long second){
        stringRedisTemplate.opsForHash().increment(channelResultKey(payType,channelNo,second),type,1);
    }
    /**
     * 获取渠道请求结果
     * @param payType 支付类型
     * @param channelNo 渠道编号
     * @param second 时间戳（秒）
     * @return 请求结果
     */
    public Map<String,Long> getChannelResult(String payType,String channelNo,long second){
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(channelResultKey(payType, channelNo, second));
        return entries.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> (String) e.getKey(),
                        e -> (Long) e.getValue())
                );
    }

    /**
     * 获取渠道请求结果
     * @param sign 支付方式-渠道编号
     * @param second 时间戳（秒）
     * @return 请求结果
     */
    public Map<String,Long> getChannelResult(String sign,long second){
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(CHANNEL_ALARM + ":result:"+sign+":"+second);
        return entries.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> (String) e.getKey(),
                        e -> Long.valueOf((String) e.getValue()))
                );
    }

    /**
     * 获取渠道请求结果
     * @param payType 支付类型
     * @param channelNo 渠道编号
     * @return 请求结果
     */
    public Map<String,Map<Object,Object>> getChannelResult(String payType,String channelNo){
        Map<String, Map<Object,Object>> entries =  new HashMap<>();
        Set<String> keys = stringRedisTemplate.keys(channelResultPrefix(payType, channelNo)+"*");
        assert keys != null;
        keys.forEach(item->{
            Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(item);
            entries.put(item,map);
        });
        return entries;
    }
    /**
     * 获取渠道请求结果
     * @param sign  支付方式-渠道编号
     * @return 请求结果
     */
    public Map<String,Map<Object,Object>> getChannelResult(String sign){
        Map<String, Map<Object,Object>> entries =  new HashMap<>();
        Set<String> keys = stringRedisTemplate.keys(CHANNEL_ALARM + ":result:"+sign+"*");
        assert keys != null;
        keys.forEach(item->{
            Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(item);
            entries.put(item,map);
        });
        return entries;
    }
    /**
     * 渠道请求结果key
     * @param payType 支付类型
     * @param channelNo 渠道编号
     * @param second 时间戳（秒）
     * @return Redis key
     */
    private String channelResultKey(String payType, String channelNo,long second) {
        return channelResultPrefix(payType,channelNo)+second;
    }
    /**
     * 渠道请求结果key前缀
     * @param payType 支付类型
     * @param channelNo 渠道编号
     * @return Redis key
     */
    private String channelResultPrefix(String payType, String channelNo) {
        return CHANNEL_ALARM + ":result:"+payType+":"+channelNo+":";
    }
    /**
     * 渠道请求的时间戳（秒）key
     * @param payType 支付类型
     * @param channelNo 渠道编号
     * @return key
     */
    private String channelTimeKey(String payType,String channelNo){
        return CHANNEL_ALARM + ":time:"+payType+":"+channelNo;
    }
    /**
     * 渠道请求的时间戳（秒）key
     * @param sign 支付方式-渠道编号
     * @return key
     */
    private String channelTimeKey(String sign){
        return CHANNEL_ALARM + ":time:"+sign;
    }
}
