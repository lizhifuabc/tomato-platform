local stock_key = KEYS[1] -- 商品库存 key
local user_key = KEYS[2] -- 用户已购买商品 key
local stock = tonumber(redis.call('hget', stock_key,'seckillRemaining')) -- 获取当前库存
local user_id = ARGV[1] -- 用户 ID

-- 如果库存不足，则返回错误码
if stock <= 0 then
    return -1
end

-- 如果用户已经购买了商品，则直接返回
if redis.call('sismember', user_key, user_id) == 1 then
    redis.log(redis.LOG_WARNING, 'stock_key:' .. stock_key, 'user_key:' .. user_key, 'stock:' .. stock, 'user_id:' .. user_id)
    return 0
end

-- 减少库存，并将用户 ID 添加到已购买商品中
-- HINCRBY SECKILL:GOODS:SECKILL:1 seckillRemaining -1
redis.call('HINCRBY', stock_key,'seckillRemaining',-1)

redis.call('sadd', user_key, user_id)

-- 返回成功码
return 1
