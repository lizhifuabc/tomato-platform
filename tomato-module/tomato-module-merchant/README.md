# 商户体系

商户管理: 支持商户入驻，商户需要向平台方提供相关的资料备案。

```java
/tomato-module-merchant/
|-- tomato-module-merchant-start/       【springboot项目】是admin端的业务逻辑，最终以springboot 启动
|-- tomato-module-merchant-base/        【jar项目】是通用的类和业务逻辑，最终以jar形式，被start或者其他项目使用
|-- pom.xml             								【pom.xml文件】父级pom.xml文件，定义共用依赖、模块、多环境profile
```

## base

- 通用的无状态的类，如：javabean对象、常量、异常、枚举、错误码、工具类、序列化类等等
- 通用的配置，如：mybatis、心跳、数据库、http、环境变量、reload、重复提交 等等配置
- 通用支撑类的业务逻辑，如：缓存、文件上传、验证码、数据字典、操作记录、token、序列号、加密 等等
- 全局的常量维护，如：redis key前缀、错误码范围、url前缀 等等

## Docker 执行

mvn:构建：
mvn clean install -Dmaven.test.skip=true

构建镜像(tomato:v1):
docker build -t tomato:v1 /Users/lizhifu/Documents/workspace/tomato-platform/tomato-module/tomato-module-merchant/tomato-module-merchant-start

启动容器（后台启动一个名为 tomato 的容器，使用 tomato:v1 镜像，并将容器的端口 9999 映射到主机的端口 9999）:
docker run -d -p 9999:9999 --name tomato tomato:v1 (默认执行了 Dockerfile 中的 CMD 命令）
docker run -d --network=host -p 9999:9999 --name tomato tomato:v1 (默认执行了 Dockerfile 中的 CMD 命令）

1. 重要：docker的IP 虚拟机中docker承载的微服务注册到nacos无法访问问题，需要在docker run的时候加上 --network=host
2. docker 在 linux 系统 下才支持 host 模式。mac 无法使用
3. 采用 bridge模式，做端口映射,将容器的端口号映射到宿主机的端口号，在被访问时通过宿主机的IP和端口来访问.
4. springcloud微服务时，单个服务在向注册中心注册的时候可以指定注册ip
   java -jar -Dspring.cloud.nacos.discovery.ip=192.168.31.122 app.jar


进入容器:
docker exec -it tomato /bin/bash

启动容器：
docker start tomato

停止容器：
docker stop tomato

删除容器：
docker rm tomato
