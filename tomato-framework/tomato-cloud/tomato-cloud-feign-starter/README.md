# OpenFeign

Spring Cloud OpenFeign 声明式服务调用与负载均衡组件。底层基于 Netflix Feign。Netflix Feign 是 Netflix 设计的开源的声明式 WebService 客户端，用于简化服务间通信。

Spring Cloud openfeign 对 Feign 进行了增强，使其支持 Spring MVC 注解，另外还整合了 Ribbon 和 Nacos，从而使得 Feign 的使用更加方便。



## 客户端依赖

```xml
<!--OpenFeign-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

## @FeignClient 注解属性

**contextId：** beanName。

**fallback：** 当调用远程接口失败或超时时，会调用对应接口的容错逻辑，fallback指定的类必须实现@FeignClient标记的接口

**fallbackFactory：** 工厂类，用于生成fallback类示例，通过这个属性我们可以实现每个接口通用的容错逻辑，减少重复的代码

**url：** url一般用于调试，可以手动指定@FeignClient调用的地址

## CircuitBreaker 断路器

1. 当断路器打开或出现错误时执行的默认逻辑，可以通过`fallback`来配置。

2. 如果需要访问导致回退触发的原因，可以使用`fallbackFactory`属性

## 开启日志

OpenFeign 的调用默认不打日志。

- **NONE** 不打日志，默认值
- **BASIC** 只记录 method、url、响应码，执行时间
- **HEADERS** 只记录请求和响应的 header
- **FULL** 全部都记录

springboot 默认日志级别是 info，如果开启 debug，需要配合 spring 使用：

```xml
logging:
    level:
    com.base.service: debug # 这里是openfeign client 所在的包路径
```

## 性能优化

1. 替换默认通信组件

2. 数据压缩

3. 负载均衡

   出于性能方面的考虑，我们可以选择用**权重策略或区域敏感策略来替代轮询策略**，因为这样的执行效率最高。

   WeightedResponseTimeRule，根据每个服务提供者的响应时间分配一个权重，响应时间越长，权重越小，被选中的可能性也就越低。它的实现原理是，刚开始使用轮询策略并开启一个计时器，每一段时间收集一次所有服务提供者的平均响应时间，然后再给每个服务提供者附上一个权重，权重越高被选中的概率也越大。

   

   BestAvailableRule，也叫最小并发数策略，它是遍历服务提供者列表，选取连接数最小的一个服务实例。如果有相同的最小连接数，那么会调用轮询策略进行选取。

   

   ZoneAvoidanceRule，根据服务所在区域(zone)的性能和服务的可用性来选择服务实例，在没有区域的环境下，该策略和轮询策略类似。

   

   AvailabilityFilteringRule，先过滤掉非健康的服务实例，然后再选择连接数较小的服务实例。

   

   RandomRule 从服务提供者的列表中随机选择一个服务实例。

   

   RetryRule，按照轮询策略来获取服务，如果获取的服务实例为 null 或已经失效，则在指定的时间之内不断地进行重试来获取服务，如果超过指定时间依然没获取到服务实例则返回 null。
