## 官方文档

“分布式服务链路跟踪”问题的流行解决方案之一。

1. 服务在跨系统的调用时想要将其串起来就需要用到 traceId 传递
2. 在内部线程之间流转就需要用到 SpanId

https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#actuator.micrometer-tracing

https://zipkin.io/

## 启动

java -jar /Users/lizhifu/Downloads/zipkin/zipkin-server-2.24.0-exec.jar

访问地址：http://127.0.0.1:9411/zipkin/

## 示例代码

[spring-boot-tracing](../../tomato-example/spring-boot-tracing)