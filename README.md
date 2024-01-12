# 聚合支付、第四方支付(仅用于学习)

仅用于学习。

## 组件说明

- 本地HOST配置
  - 127.0.0.1 tomato-nacos
  - 127.0.0.1  tomato-rabbitmq
  - 127.0.0.1 tomato-mysql

- Nacos [home (nacos.io)](https://nacos.io/zh-cn/index.html)
  - 服务注册&发现和配置中心（ 2.2.0）  
  - 更改配置文件：位置：nacos/conf/application.properties
  - 单机模式运行：sh startup.sh -m standalone
- rabbitmq [RabbitMQ: easy to use, flexible messaging and streaming — RabbitMQ](https://www.rabbitmq.com/)
  - 安装：[The Homebrew RabbitMQ Formula — RabbitMQ](https://rabbitmq.com/install-homebrew.html)
  - 延迟队列：[rabbitmq/rabbitmq-delayed-message-exchange: Delayed Messaging for RabbitMQ (github.com)](https://github.com/rabbitmq/rabbitmq-delayed-message-exchange)



> 记录本机服务启动：
>
> sh /Users/lizhifu/Downloads/cloud/nacos/bin/startup.sh -m standalone
>
> sh /Users/lizhifu/Downloads/cloud/nacos/bin/shutdown.sh
>
> 
>
> docker cp ./rabbitmq_delayed_message_exchange-3.9.0.ez some-rabbit:/plugins
>
> /Users/lizhifu/Downloads/rabbitmq_server-3.11.5
>
> ./sbin/rabbitmq-server
>
> 启动：http://localhost:15672/ guest/guesg

## 微服务架构

- API 网关（Api Gateway Pattern）
- 服务发现与注册（Service Registration and Discovery Pattern）
- 断路器（Circuit Breaker Pattern）
- 隔板模式（Bulkhead Pattern）
- 命令和查询职责分离（CQRS Pattern）
- 事件驱动模式(Dvent Driven Pattern)
- Saga 模式(Saga Pattern)
- 扼杀者模式（Strangler Pattern）
- 边车模式（Sidecar Pattern）
- BFF 模式（Backend for Frontend Pattern）

### API 网关

API网关是一种位于微服务架构或分布式系统前端的服务器，它用于管理、监控和保护应用程序中的各种API端点。API网关充当客户端和后端微服务之间的中介，它可以执行多种任务，包括路由请求、认证和授权、请求转换、负载均衡、缓存、监控和日志记录等。

**应用场景**

1. **路由和负载均衡：** API网关可以根据请求的URL、路径或其他标识符将请求路由到适当的后端微服务。它还可以执行负载均衡，将请求分配到不同的服务实例，以确保资源的有效使用和高可用性。
2. **认证和授权：** API网关可以在请求到达后端服务之前执行身份验证和授权操作，以确保只有经过授权的用户才能访问特定的API。这有助于保护应用程序免受未经授权的访问。
3. **请求转换：** API网关可以执行请求和响应的转换，将不同格式的数据转换为适合客户端的格式，从而减少后端微服务的负担，提供更好的客户端体验。
4. **缓存：** API网关可以实现缓存策略，将频繁请求的响应缓存起来，从而降低后端服务的负载，提高性能和响应时间。
5. **安全性：** API网关可以集中处理安全性问题，例如防止DDoS攻击、保护敏感信息，以及实施防火墙和黑名单/白名单等安全措施。
6. **监控和分析：** API网关可以收集和监控请求和响应的数据，提供有关API使用情况、性能和错误的详细信息。这有助于识别瓶颈、调整资源分配，并进行故障排除。
7. **简化客户端：** API网关可以将多个微服务的复杂性对客户端进行抽象，从而简化客户端的调用和交互过程。
8. **版本控制：** API网关可以支持API的版本管理，允许不同版本的API同时存在并进行平稳迁移。

###  服务注册与发现

服务注册与发现是微服务架构中的一个重要概念，用于管理和定位分布式系统中的各个微服务实例。它解决了在动态环境中，如何使微服务能够发现彼此并建立通信的问题。

1. **服务注册：** 当一个微服务启动时，它会将自己的元数据（例如主机名、端口、API路径等）注册到一个中心化的服务注册表中。这个注册表可以是一个独立的组件，也可以是由某个云平台或第三方工具提供的服务。
2. **服务发现：** 其他微服务可以查询服务注册表，以获取需要调用的目标微服务的位置信息。这些查询可以是通过HTTP、DNS或其他协议来实现的。
3. **动态适应：** 当微服务的实例数量发生变化（例如扩容、缩容或故障恢复），服务注册表会自动更新，使其他微服务能够感知这些变化，从而确保通信路径的准确性。

### 断路器

断路器（Circuit Breaker）是一种用于改善分布式系统稳定性和容错性的设计模式，特别在微服务架构中得到广泛应用。它的目标是防止系统在发生故障或异常情况时继续尝试执行可能会失败的操作，从而减少对整体系统的影响，提高系统的可用性和健壮性。

断路器模式的核心思想类似于电气电路中的断路器，当系统达到某个故障阈值时，断路器会打开，阻止继续尝试执行操作，而是立即返回一个错误响应。这样可以避免雪崩效应，即一个故障引起连锁反应，导致更多的服务失败。

1. **关闭状态（Closed）：** 在正常情况下，断路器处于关闭状态，允许请求通过并监视服务的响应时间和错误率。
2. **打开状态（Open）：** 当错误率或响应时间超过设定的阈值时，断路器会切换到打开状态，阻止请求通过。在此状态下，系统会快速返回错误响应，而不会继续尝试执行操作。
3. **半开状态（Half-Open）：** 在一段时间后，断路器可以自动或手动切换到半开状态，允许一部分请求通过，以测试服务是否恢复正常。如果请求成功，则断路器可能会重新关闭；如果失败，则会重新打开。

### 隔板模式

一种在分布式系统中用于提高稳定性和隔离性的设计模式。该模式的核心思想是将不同的组件或服务放置在独立的隔板（bulkhead）中，以防止一个组件的故障影响其他组件，从而提高整个系统的可用性。

在 Resilience4j 中，断路器和限流策略可以帮助您实现隔板模式的一部分。以下是一些 Resilience4j 的功能，可以帮助您实现隔板模式的核心思想：

1. **断路器模式：** Resilience4j 提供了断路器模式的实现，可以在调用失败率达到一定阈值时打开断路器，阻止进一步的请求通过，从而实现隔板效果。
2. **限流模式：** 通过 Resilience4j 的限流策略，您可以控制每个隔板中的资源分配，防止某个隔板中的服务占用过多资源，从而影响其他隔板。
3. **超时策略：** Resilience4j 允许您为调用设置超时，以防止某个隔板中的调用长时间阻塞，影响其他隔板中的服务。
4. **重试机制：** Resilience4j 提供了重试功能，当一个隔板中的调用失败时，可以进行重试，以增加成功的概率。

### 命令和查询职责分离

命令和查询职责分离（CQRS，Command Query Responsibility Segregation）是一种在软件架构中的设计原则，旨在将系统的读取操作（查询）和写入操作（命令）分开，以优化系统的性能、可伸缩性和维护性。

CQRS 的核心思想是，将读取和写入操作分别处理，这样可以针对不同的需求进行优化，从而提供更好的性能和用户体验。这种分离有助于消除在单一模型上同时进行读取和写入所带来的复杂性问题。

**缺点**

- 需要维护多个数据源之间的同步和一致性，增加了系统的复杂度和开发成本。
- 需要处理数据延迟和最终一致性的问题，可能影响用户体验和业务逻辑。

### 事件驱动模式

事件驱动模式是一种软件架构和设计范式，其中系统的不同组件通过事件的产生、传递和处理来进行通信和协作。在事件驱动模式中，组件之间通过发布和订阅事件的方式进行解耦，从而实现松散耦合、灵活性和可扩展性。

1. **事件（Event）：** 事件是系统中发生的某种事情或状态变化的表示。它可以是一个简单的通知，也可以携带数据。事件可以被发布，以通知其他组件发生了某种变化。
2. **发布者（Publisher）：** 发布者负责生成并发布事件。它是事件的源头，可以是某个组件、服务或模块。
3. **订阅者（Subscriber）：** 订阅者订阅感兴趣的事件，以便在事件发生时得到通知并执行相应的操作。订阅者可以是其他组件、服务或模块。
4. **事件总线（Event Bus）：** 事件总线是用于传递事件的中介。它负责事件的分发和通知，确保事件从发布者传递到订阅者。

### Saga 模式

Saga 模式是一种在分布式系统中处理长事务（Long-Running Transactions）的设计模式。长事务指的是需要跨足够长时间执行的一系列相关操作，通常涉及多个步骤，可能会涉及多个服务或组件。

Saga 模式旨在解决分布式事务的问题，因为传统的 ACID 事务在分布式系统中往往面临性能和可扩展性等挑战。Saga 模式将长事务拆分成一系列的小事务，每个小事务称为一个步骤（Step），每个步骤可以在独立的事务中执行。每个步骤都负责完成一部分工作，并记录自己的执行状态。

### 扼杀者模式

"扼杀者模式"（Antipattern）是软件工程中的一个术语，指的是常见的、被广泛接受但却被证明是不良实践的设计、开发或架构方式。扼杀者模式描述了在解决问题时，可能会导致系统变得难以理解、维护和扩展的方法。它是一种反面教材，帮助开发人员避免常见的陷阱和错误。

扼杀者模式的特点包括：

1. **看似正确：** 扼杀者模式通常是在一开始看起来合理和有效的解决方案，但随着时间的推移和系统的增长，它们可能会显露出问题。
2. **短期收益：** 扼杀者模式可能会在短期内提供一些好处，但在长期内可能会导致系统的不稳定性、低效性或难以维护性。
3. **违反最佳实践：** 扼杀者模式通常会违反软件工程中的最佳实践，如模块化、松散耦合、单一职责等原则。
4. **重复出现：** 扼杀者模式在不同的项目中可能会反复出现，表明它们是一种常见的误解或误用。

一些常见的扼杀者模式包括：

- **神奇数字：** 在代码中硬编码一些常数或数字，使得代码难以理解和维护。
- **大而全模块：** 创建单个巨大的模块或类，处理过多的功能，导致耦合度高、难以测试和重用。
- **复制粘贴编程：** 复制和粘贴现有代码片段，导致重复、冗余和难以维护的代码。
- **忽略异常：** 不适当地忽略异常或错误，导致系统无法适应异常情况。
- **过度优化：** 过早地进行性能优化，可能导致代码复杂化、可读性降低和不必要的复杂性。
- **深度嵌套：** 过多的嵌套层次，使得代码难以阅读和理解。

###  边车模式

边车模式是一种在微服务架构中常见的设计模式，它可以帮助实现业务逻辑与通用功能的解耦，从而提高系统的可维护性、扩展性和灵活性。通过将与业务无关的功能从主应用程序中分离出来，边车服务可以为主应用程序提供支持，同时保持独立性。

边车模式可以应用于各种场景，包括日志收集、监控、配置管理、安全认证等。它能够使主应用程序更专注于核心业务逻辑，而将通用功能委托给边车服务来处理

### BFF 模式

BFF 模式是“Backend for Frontend”的缩写，指的是为前端（Frontend）应用程序定制的后端（Backend）服务。BFF 模式旨在解决在复杂的前端应用程序中，前端和后端之间的需求和交互的不一致性问题。

在传统的单一后端架构中，前端应用程序需要与单一后端服务进行通信，这可能导致以下问题：

1. **Over-fetching 和 Under-fetching：** 前端可能需要的数据可能超出或不足于后端提供的数据，导致性能和带宽浪费。
2. **频繁变更和维护：** 前端和后端通常有不同的需求和周期，频繁的变更可能会导致双方的协调和维护困难。
3. **性能问题：** 在高并发情况下，前端请求可能会导致后端过载，影响系统的性能。

BFF 模式通过为前端应用程序定制专门的后端服务，解决了这些问题。每个前端应用程序都可以有一个或多个特定的 BFF 服务，它们负责满足前端的具体需求，提供精确的数据和功能。这些 BFF 服务可以将多个后端服务的数据整合、聚合或转换，从而提供更高效、更定制的数据响应。

BFF 模式的优点包括：

- **定制化：** BFF 可以为每个前端应用程序提供定制化的数据和功能，避免了过度获取或获取不足的问题。
- **减少耦合：** BFF 服务将前端与后端之间的需求分离，降低了双方之间的耦合度，使得每个团队可以更独立地进行开发和变更。
- **性能优化：** BFF 可以根据前端的需求进行性能优化，提供更快的响应和更少的带宽消耗。
- **更好的维护性：** BFF 将前端和后端的交互逻辑集中在一个地方，使得变更和维护更加集中和可控。

需要注意的是，BFF 模式需要根据实际情况进行设计和实施。每个前端应用程序可能需要一个或多个 BFF 服务，而后端服务可能需要相应地进行拆分和调整。BFF 模式通常与微服务架构和 API 网关一起使用，以实现更灵活、高效的前后端交互。

###  Spring 官方文档

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
- MySQL 同步 ES 方案（ TODO）：Canal（https://github.com/alibaba/canal）、dolphinscheduler（https://github.com/apache/dolphinscheduler）
## 截图

![spring-boot-admin.png](doc%2Fimage%2Fspring-boot-admin.png)

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
├     ├── ├── tomato-cloud-feign-starter -- feign 相关依赖
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