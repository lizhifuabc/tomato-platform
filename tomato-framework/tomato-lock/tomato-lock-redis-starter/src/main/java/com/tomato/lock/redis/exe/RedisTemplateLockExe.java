package com.tomato.lock.redis.exe;

import com.tomato.lock.core.exe.AbstractLockExe;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Collections;

/**
 * RedisTemplate：分布式锁
 * <p>原子性：lua脚本执行时会将脚本作为一个整体执行，中间不会被其他命令插入</p>
 * <p>缺点：只作用在一个Redis节点上，例如 master 节点发生了主从切换，分布式锁key尚未同步到slave节点，此时数据消失</p>
 * <p></p>
 * @author lizhifu
 * @since 2023/1/17
 */
public class RedisTemplateLockExe extends AbstractLockExe<Boolean> {
    private final StringRedisTemplate stringRedisTemplate;
    private final DefaultRedisScript<Boolean> LOCK_SCRIPT;
    private final DefaultRedisScript<Long> DELETE_LOCK_SCRIPT;

    public RedisTemplateLockExe(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        LOCK_SCRIPT = new DefaultRedisScript<>();
        LOCK_SCRIPT.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("META-INF/scripts/lock_script.lua")));
        LOCK_SCRIPT.setResultType(Boolean.class);

        DELETE_LOCK_SCRIPT = new DefaultRedisScript<>();
        DELETE_LOCK_SCRIPT.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("META-INF/scripts/delete_lock_script.lua")));
        DELETE_LOCK_SCRIPT.setResultType(Long.class);
    }

    @Override
    public Boolean lock(String lockKey, String lockValue, long expire, long acquireTimeout) {
        Boolean lock = stringRedisTemplate.execute(LOCK_SCRIPT,
                Collections.singletonList(lockKey),
                lockValue, String.valueOf(expire));
        return lock;
    }

    @Override
    public boolean unLock(String lockKey, String lockValue, Boolean lockInstance) {
        Long releaseResult = stringRedisTemplate.execute(DELETE_LOCK_SCRIPT,
                Collections.singletonList(lockKey), lockValue);
        return releaseResult == 1;
    }
}
