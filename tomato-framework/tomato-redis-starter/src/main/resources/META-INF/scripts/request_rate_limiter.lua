-- redis.replicate_commands() 是 Redis 客户端的一个方法，它用于启用命令复制（command replication）。
-- 命令复制是指，在多个 Redis 实例之间复制命令，以保证数据的一致性。
-- 例如，如果你在一个 Redis 集群中执行了一条写入命令，那么这条命令就会被复制到集群中的其他实例中。
-- 这样，就可以保证集群中的所有实例都保存了相同的数据，并且可以提供高可用性和数据安全性。
redis.replicate_commands()
-- 记录桶的剩余容量
local tokens_key = KEYS[1]
-- 记录桶上次刷新时间，以此推算当前需要填入的令牌数量
-- 第一次：需要新填入的令牌数量 = (当前时间 - 0) * 速率
-- 其他后：需要新填入的令牌数量 = (当前时间 - Key[2]) * 速率
local timestamp_key = KEYS[2]
-- 综上：**当前桶内可用令牌数 = 桶的剩余容量 + 需要新填入的令牌数量**
redis.log(redis.LOG_WARNING, "tokens_key " .. tokens_key)

local rate = tonumber(ARGV[1])
local capacity = tonumber(ARGV[2])
local now = redis.call('TIME')[1]
local requested = tonumber(ARGV[4])

local fill_time = capacity/rate
-- redis key[1]、key[2] 的过期时间
-- 令牌过期时间：填充时间*2
-- 返回小于参数x的最大整数
-- 这样可以保证令牌桶中的令牌能够被充分利用，并避免过早的过期。
-- 例如，如果填充时间的值为 10 秒，那么过期时间的值就应该设置为 20 秒。这样，在令牌桶的生存周期内，用户就有足够的时间来使用令牌桶中的令牌。
local ttl = math.floor(fill_time*2)

redis.log(redis.LOG_WARNING, "rate " .. ARGV[1])
redis.log(redis.LOG_WARNING, "capacity " .. ARGV[2])
redis.log(redis.LOG_WARNING, "now " .. now)
redis.log(redis.LOG_WARNING, "requested " .. ARGV[4])
redis.log(redis.LOG_WARNING, "filltime " .. fill_time)
redis.log(redis.LOG_WARNING, "ttl " .. ttl)

local last_tokens = tonumber(redis.call("get", tokens_key))
if last_tokens == nil then
    last_tokens = capacity
end
redis.log(redis.LOG_WARNING, "last_tokens " .. last_tokens)

local last_refreshed = tonumber(redis.call("get", timestamp_key))
if last_refreshed == nil then
    last_refreshed = 0
end
redis.log(redis.LOG_WARNING, "last_refreshed " .. last_refreshed)

local delta = math.max(0, now-last_refreshed)
local filled_tokens = math.min(capacity, last_tokens+(delta*rate))
local allowed = filled_tokens >= requested
local new_tokens = filled_tokens
local allowed_num = 0
if allowed then
    new_tokens = filled_tokens - requested
    allowed_num = 1
end

--redis.log(redis.LOG_WARNING, "delta " .. delta)
--redis.log(redis.LOG_WARNING, "filled_tokens " .. filled_tokens)
--redis.log(redis.LOG_WARNING, "allowed_num " .. allowed_num)
--redis.log(redis.LOG_WARNING, "new_tokens " .. new_tokens)

if ttl > 0 then
    redis.call("setex", tokens_key, ttl, new_tokens)
    redis.call("setex", timestamp_key, ttl, now)
end

-- return { allowed_num, new_tokens, capacity, filled_tokens, requested, new_tokens }
return { allowed_num, new_tokens }