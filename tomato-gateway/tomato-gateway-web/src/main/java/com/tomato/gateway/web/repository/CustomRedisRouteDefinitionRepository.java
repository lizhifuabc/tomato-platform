package com.tomato.gateway.web.repository;

import com.tomato.jackson.utils.JacksonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态路由管理，不重启网关的情况下，动态刷新路由配置。
 * <p>RouteDefinitionLocator:加载路由;PropertiesRouteDefinitionLocator:yml中加载路由
 * <p>RouteDefinitionWriter:路由的添加与删除
 *
 * <p>请求地址</p>
 * <p>动态路由查看: actuator/gateway/routes </p>
 * <p>添加 route 信息 post：actuator/gateway/routes/{id}</p>
 * <p>刷新路由 post：/actuator/gateway/refresh</p>
 *
 * @author lizhifu
 * @since 2023/7/12
 */
@Slf4j
@Component
public class CustomRedisRouteDefinitionRepository implements RouteDefinitionRepository {
    public static final String REDIS_GATEWAY_ROUTES = "gateway:routes";
    @Resource
    private RedisTemplate<String, RouteDefinition> redisTemplate;

    /**
     * 返回自定义的路由配置信息，可以从数据库或者缓存中取
     * @return 路由配置信息
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        redisTemplate.opsForHash().entries(REDIS_GATEWAY_ROUTES).values().forEach(routeDefinition -> routeDefinitions.add(JacksonUtils.toObject(routeDefinition.toString(), RouteDefinition.class)));
        log.debug("[动态路由管理]从redis获取所有网关路由");
        return Flux.fromIterable(routeDefinitions);
    }

    /**
     * 用于通过 RESTFul API 添加路由信息
     * <p>需要开启 actuator 的 gateway 端点
     * @param routeDefinition 路由信息
     * @return 是否保存成功
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> routeDefinition) {
        log.info("[动态路由管理]缓存新的网关路由:{}",routeDefinition);
        return routeDefinition.flatMap(route -> {
            redisTemplate.opsForHash().put(REDIS_GATEWAY_ROUTES, route.getId(), JacksonUtils.toJson(route));
            return Mono.empty();
        });
    }

    /**
     * 用于通过 RESTFul API 删除路由信息
     * <p>需要开启 actuator 的 gateway 端点
     * @param routeId 路由ID
     * @return 是否删除成功
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId
                .doOnNext(id -> {
                    log.info("[动态路由管理]Redis缓存删除的路由定义 {}", id);
                    redisTemplate.opsForHash().delete(REDIS_GATEWAY_ROUTES, id);
                })
                .then();
    }
}
