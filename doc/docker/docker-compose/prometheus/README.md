# Prometheus - 开源的系统监控和报警系统

- Docker Compose 文件定义了三个服务：`prometheus`、`node-exporter` 和 `grafana`。
- `prometheus` 服务使用 Prometheus 镜像，并将配置文件 `prometheus.yml` 映射到容器内部的配置路径。
- `node-exporter` 服务使用 Node Exporter 镜像，用于采集服务器层面的运行指标。
- `grafana` 服务使用 Grafana 镜像，通过配置文件 `grafana.ini` 和环境变量来进行配置。这里使用了 MySQL 数据库来持久化 Grafana 数据。



> `docker-compose-prometheus.yml` 需修改grafana中配置的mysql连接信息-create schema grafana;
> `prometheus.yml` 自行配置

```shell
# 运行
docker-compose -f docker-compose-prometheus.yml -p prometheus up -d
# 查看grafana日志
docker logs -fn10 prometheus-grafana
```

1. grafana访问地址：http://localhost:3000/
   默认登录账号密码：`admin/admin`
2. prometheus访问地址:http://localhost:9090/
3. exporter访问地址: http://localhost:9100/metrics
4. pushgateway: http://localhost:9091

## 其它

grafana面板资源 https://grafana.com/grafana/dashboards

node-exporter =》 https://grafana.com/grafana/dashboards/8919