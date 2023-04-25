# 使用

Micrometer是Spring Boot的默认度量库，它已经集成到Spring Boot中。

https://github.com/micrometer-metrics/micrometer

Spring Boot中使用Micrometer：

1. Micrometer核心依赖项：

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-core</artifactId>
</dependency>
```

2. Prometheus监控：

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

3. 配置Micrometer：Micrometer与Prometheus集成：

```properties
management.endpoint.metrics.enabled=true
management.endpoints.web.exposure.include=*
management.metrics.export.prometheus.enabled=true
```

4. 自定义度量指标：使用@Timed、@Counter等注释定义自定义度量指标。

```java
@RestController
@RequestMapping("/api")
public class MyController {

    private final Timer requestLatency;

    public MyController(MeterRegistry registry) {
        this.requestLatency = registry.timer("request.latency");
    }

    @GetMapping("/users")
    @Timed(value = "request.latency", description = "Time taken to get all users")
    public List<User> getAllUsers() {
        // Code to get all users
    }
}
```

5. 可视化和分析度量指标：使用Prometheus等工具可视化和分析度量指标。

