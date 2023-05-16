https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#actuator.metrics.export.prometheus


http://127.0.0.1:9996/actuator/prometheus
https://prometheus.io/

# 启用 prometheus和health监控
management.endpoints.web.exposure.include=prometheus,health
management.prometheus.metrics.export.enabled=true