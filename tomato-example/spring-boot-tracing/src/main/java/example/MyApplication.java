package example;

/**
 * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#actuator.micrometer-tracing
 *
 * @author lizhifu
 * @since 2023/4/26
 */
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 跟踪（trace）和 跨度（ span）
 * trace 是请求在分布式系统中的整个链路视图，span 则代表整个链路中不同服务内部的视图，span 组合在一起就是整个 trace 的视图。
 */
@RestController
@SpringBootApplication
public class MyApplication {
    @Resource
    private Tracer tracer;

    private static final Log logger = LogFactory.getLog(MyApplication.class);

    @RequestMapping("/")
    String home() {
        Span span = tracer.currentSpan();
        if (Objects.nonNull(span)) {
            logger.info("span id: {}" + span.context().spanId());
            logger.info("span trace id: {}" + span.context().traceId());
        }
        logger.info("home() has been called");
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

}

