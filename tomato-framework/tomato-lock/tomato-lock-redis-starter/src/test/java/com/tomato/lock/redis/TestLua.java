package com.tomato.lock.redis;

/**
 * lua
 *
 * @author lizhifu
 * @since 2023/1/17
 */
public class TestLua {
    /**
     * 获取分布式锁的lua脚本
     * <p>
     * 解释：先获取分布式key对应的value和当前clientId比较，如果相等，重新设置失效时间，之后没有value就设置值，否则获取不到值
     * <p>
     * 这里设置分布式锁使用set命令而没用setnx是因为Redis 2.6.12版本后，set命令整合了setex的功能，并且set本身就已经包含了设置过期时间，
     * 因此常说的set命令加上选项已经完全可以取代setnx命令，并且在将来的版本中，Redis可能不推荐并最终抛弃
     * 官方文档如下：
     * 中文文档：http://www.redis.cn/commands/set.html
     * 英文文档：https://redis.io/commands/set
     */
    private static final String OBTAIN_LOCK_SCRIPT =
            "local lockClientId = redis.call('GET', KEYS[1])\n" +
                    "if lockClientId == ARGV[1] then\n" +
                    "  redis.call('PEXPIRE', KEYS[1], ARGV[2])\n" +
                    "  return true\n" +
                    "elseif not lockClientId then\n" +
                    "  redis.call('SET', KEYS[1], ARGV[1], 'PX', ARGV[2])\n" +
                    "  return true\n" +
                    "end\n" +
                    "return false";

    /**
     * 使用lua脚本保证原子删除
     * <p>
     * 解释：如果对应的value等于传入的uuid，执行删除，不成功返回0
     */
    private static final String DELETE_LOCK_SCRIPT =
            "       if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "  return redis.call('del', KEYS[1])" +
                    "else return 0 end";
    public static void main(String[] args) {
        System.out.println(OBTAIN_LOCK_SCRIPT);
    }
}
