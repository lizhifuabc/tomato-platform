# 聚合支付、第四方支付(仅用于学习)

仅用于学习。

spring 官方文档记录：

[Spring | Home](https://spring.io/)

[Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)

[Spring Cloud](https://docs.spring.io/spring-cloud/docs/current/reference/htmlsingle/)

[Spring Cloud Gateway](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/)

[Spring Cloud OpenFeign](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/)

## 技术框架

- 持久层框架： Spring Data Jpa & Mybatis
- API 网关：Spring Cloud Gateway
- 服务注册&发现和配置中心: Alibaba Nacos
- 服务消费：Spring Cloud OpenFeign & RestTemplate & OkHttps
- 负载均衡：Spring Cloud Loadbalancer
- 服务熔断&降级&限流：Alibaba Sentinel、Spring Cloud Circuitbreaker Resilience4j
- 服务监控：Spring Boot Admin
- 参数校验：Spring Validation
- 权限框架：Spring Security
- 消息队列：RabbitMQ
- 链路跟踪：Skywalking、Zipkin
- 分布式事务：Seata
- 数据缓存：JetCache (Redis + Caffeine) 多级缓存
- 数据库： MySQL
- JSON 序列化：Jackson
- 文件服务：阿里云 OSS/Minio
- 数据调试（ TODO）：p6spy
- 日志中心（ TODO）：ELK
- 日志收集（ TODO）：Logstash Logback Encoder

## 基础文档

[多环境配置.md](doc%2F%E5%A4%9A%E7%8E%AF%E5%A2%83%E9%85%8D%E7%BD%AE.md)


## 模块介绍

```text
tomato-platform
├── spring-boot-cli-demo -- 基于 maven-archetype-plugin 脚手架
├── tomato-bom -- 项目 POM 版本管理
└── tomato-framework -- 系统公共模块
├     ├── tomato-tracing-starter -- 基于Micrometer、Zipkin分布式链路追踪
├     ├── tomato-cloud -- cloud 配套组件
├     ├── ├── tomato-cloud-alibaba-starter -- alibaba 配套组件
├     ├── ├── tomato-cloud-feign-stater -- feign 相关依赖
├     ├── tomato-data -- orm 相关
├     ├── ├── tomato-mybatis-starter -- 基于SqlProvider的 MyBatis 增强(简易)
├     ├── ├── XXXXXX -- XXXXXX
├     ├── tomato-rabbitmq-starter -- rabbitmq配置|动态队列、动态生产者，动态消费者绑定
├     └── XXXXXX
├── tomato-gateway -- 网关服务
├     ├── tomato-gateway-core -- 服务网关核心依赖
├		  ├── tomato-gateway-web -- 系统服务网关[9999]
├── XXXXXX -- XXXXXX
└── tomato-example -- 示例代码
├     └── spring-boot-tracing --  基于Micrometer、Zipkin分布式链路追踪例子
├     └── spring-modulith -- Spring Boot的模块化应用程序示例
└── tomato-module -- 业务模块
├     └── tomato-module-account -- 账户服务[9080]
├     ├── tomato-module-monitor -- 基于 Spring Boot Admin 监控服务[9994]
├     ├── tomato-module-notice  -- 通知系统[9996]
├     ├── tomato-module-pay     -- 支付通道[9998]
├     ├── tomato-module-pay-monitor  -- 支付通道监控[9997]
├     └── tomato-module-reconciliation -- 对账中心[9995]
├     └── tomato-module-sys -- 后台管理系统（RABC）[8001]
```

## 涉及角色

1. 聚合支付平台（支付平台）
2. 商户平台：普通商户、代理商商户
3. 用户：消费者
4. 资金相关：第三方支付（微信、支付宝等等）；银行；银联

## 资金流

聚合支付业务：

![消费.png](doc%2Fimage%2F%E6%B6%88%E8%B4%B9.png)
支付业务：

![资金流2.png](doc%2Fimage%2F%E8%B5%84%E9%87%91%E6%B5%812.png)

##  对账系统（对账中心）

![对账系统概念.png](doc%2Fimage%2F%E5%AF%B9%E8%B4%A6%E7%B3%BB%E7%BB%9F%E6%A6%82%E5%BF%B5.png)

在银行或者第三方支付中，对账其实是对一定周期内的交易进行双方确认的过程，一般都是在第二天银行或者第三方支付公司对前一日交易进行清分，生成对账单供平台商户下载，并将应结算款结算给平台商户。

在往下一层，在互联网金融行业或者电商行业中，对账其实就是确认在固定周期内和支付提供方（银行和第反方支付）的交易、资金的正确性，保证双方的交易、资金一致正确。





## 账户系统

账户入账：

![trade.png](doc%2Fimage%2Ftrade.png)




定时相关：

- [x] 账户异步入账服务定时：AccountTradAsyncTimer

- [x] 账户结算定时：AccountSettleTimer
- [x] 风险预存期外余额定时：AccountOutReserveBalanceTimer

账户基本操作：

- [ ] 创建账户
  - [x] 基本创建
  - [ ] 账户编号 accountNo 生成规则修改。
- [x] 注销账户
- [x] 冻结止收
- [x] 冻结止付
- [x] 完全冻结
- [x] 解冻

账户费率：本质是为了扩展功能，支付系统中，例如结算是没有手续费的，交易是在交易系统进行扣除手续费。

- [x] 费率初始化
- [ ] 费率修改

账户交易相关：

- [x] 交易
- [x] 提现
- [ ] 退款
- [ ] 结算
- [ ] 调账

## 通知系统

核心收单：

- [x] 同步收单，基于RestTemplate，重试基于MQ
- [x] 异步收单，基于WebClient，重试基于本地。

## 支付系统
## 代理商系统
## 打款系统
## 订单系统
## 通道系统
## 风控系统
## 商户系统
## 秒杀系统
## 商品系统