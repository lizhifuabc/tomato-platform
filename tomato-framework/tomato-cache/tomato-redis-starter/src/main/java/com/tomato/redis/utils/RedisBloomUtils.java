package com.tomato.redis.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis 布隆过滤器
 *
 * @author lizhifu
 * @since 2023/8/30
 */
public class RedisBloomUtils {
	private final StringRedisTemplate stringRedisTemplate;

	public RedisBloomUtils(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 调用 Redis 命令 BF.RESERVE 来创建一个新的布隆过滤器
	 */
	private static final RedisScript<Boolean> BF_RESERVE_SCRIPT =
			new DefaultRedisScript<>("return redis.call('bf.reserve', KEYS[1], ARGV[1], ARGV[2])", Boolean.class);

	/**
	 * 调用 Redis 命令 BF.ADD 来将一个元素添加到布隆过滤器中
	 */
	private static final RedisScript<Boolean> BF_ADD_SCRIPT =
			new DefaultRedisScript<>("return redis.call('bf.add', KEYS[1], ARGV[1])", Boolean.class);

	/**
	 * 调用 Redis 命令 BF.EXISTS 来检查一个元素是否存在于布隆过滤器中
	 */
	private static final RedisScript<Boolean> BF_EXISTS_SCRIPT =
			new DefaultRedisScript<>("return redis.call('bf.exists', KEYS[1], ARGV[1])", Boolean.class);

	/**
	 * 执行 Redis 命令 BF.MADD，它会批量添加多个元素到布隆过滤器中，参数占位符 %s
	 */
	private static final String BF_MADD_SCRIPT = "return redis.call('bf.madd', KEYS[1], %s)";

	/**
	 * 执行 Redis 命令 BF.MEXISTS，它会批量检查多个元素是否存在于布隆过滤器中，参数占位符 %s
	 */
	private static final String BF_MEXISTS_SCRIPT = "return redis.call('bf.mexists', KEYS[1], %s)";

	/**
	 * 设置错误率和大小（需要在添加元素前调用，若已存在元素，则会报错）
	 * 错误率越低，需要的空间越大
	 * @param key key
	 * @param errorRate 错误率，默认0.01
	 * @param initialSize 默认100，预计放入的元素数量，当实际数量超出这个数值时，误判率会上升，尽量估计一个准确数值再加上一定的冗余空间
	 * @return 是否设置成功
	 */
	public Boolean bfReserve(String key, double errorRate, int initialSize){
		return stringRedisTemplate.execute(BF_RESERVE_SCRIPT, Collections.singletonList(key), String.valueOf(errorRate), String.valueOf(initialSize));
	}

	/**
	 * 添加元素
	 * @param key key
	 * @param value value
	 * @param timeout 超时时间，单位小时
	 * @return true表示添加成功，false表示添加失败（存在时会返回false）
	 */
	public Boolean bfAdd(String key, String value, long timeout){
		Boolean hasKey = stringRedisTemplate.hasKey(key);
		Boolean success = stringRedisTemplate.execute(BF_ADD_SCRIPT, List.of(key), value);
		// TODO 使用脚本的方式执行超时
		if(Boolean.FALSE.equals(hasKey)){
			stringRedisTemplate.expire(key, timeout, TimeUnit.HOURS);
		}
		return success;
	}
	/**
	 * 查看元素是否存在（判断为存在时有可能是误判，不存在是一定不存在）
	 * @param key key
	 * @param value value
	 * @return true表示存在，false表示不存在
	 */
	public Boolean bfExists(String key, String value){
		return stringRedisTemplate.execute(BF_EXISTS_SCRIPT, Collections.singletonList(key), value);
	}

	/**
	 * 批量添加元素
	 * @param key key
	 * @param values value
	 * @return 按序 1表示添加成功，0表示添加失败
	 */
	public List<Integer> bfMAdd(String key, String... values){
		return (List<Integer>)stringRedisTemplate.execute(this.generateScript(BF_MADD_SCRIPT, values), Collections.singletonList(key), values);
	}

	/**
	 * 批量检查元素是否存在（判断为存在时有可能是误判，不存在是一定不存在）
	 * @param key key
	 * @param values value
	 * @return 按序 1表示存在，0表示不存在
	 */
	public List<Integer> bfMExists(String key, String... values){
		return (List<Integer>)stringRedisTemplate.execute(this.generateScript(BF_MEXISTS_SCRIPT, values), Collections.singletonList(key), values);
	}

	/**
	 * 生成脚本
	 * @param script 脚本
	 * @param values 参数
	 * @return 脚本
	 */
	private RedisScript generateScript(String script, String[] values) {
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= values.length; i ++){
			if(i != 1){
				sb.append(",");
			}
			sb.append("ARGV[").append(i).append("]");
		}
		return new DefaultRedisScript<>(String.format(script, sb), List.class);
	}
}
