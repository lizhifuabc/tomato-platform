# spring boot3.X Tracing

分布式链路追踪。

依赖：

```xml
<dependencies>
  <!-- spring boot web 相关 -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <!-- spring boot actuator 相关 -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <!-- 连接到OpenTelemetry -->
  <dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-otel</artifactId>
  </dependency>
  <!--  向Zipkin报告跟踪      -->
  <dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-zipkin</artifactId>
  </dependency>
</dependencies>
```

核心配置：

```properties
# 默认情况下，Spring Boot只对10%的请求进行采样，以防止跟踪后端不堪重负。
# 此属性将其切换为100%，以便将每个请求发送到跟踪后端。
management.tracing.sampling.probability=1.0
# 为了将跟踪信息发送到Zipkin，我们需要将spring.zipkin.base-url属性设置为Zipkin服务器的URL。
management.zipkin.tracing.endpoint=http://localhost:9411/api/v2/spans
# 日志打印traceId和spanId
logging.pattern.level=%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]
```

## OpenZipkin

[Quickstart · OpenZipkin](https://zipkin.io/pages/quickstart)

```shell
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```

访问地址：[Zipkin](http://127.0.0.1:9411/zipkin/)

