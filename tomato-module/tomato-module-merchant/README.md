# 商户体系

商户管理: 支持商户入驻，商户需要向平台方提供相关的资料备案。

## Docker 执行

mvn:构建：
mvn clean install -Dmaven.test.skip=true

构建镜像(tomato:v1):
docker build -t tomato:v1 /Users/lizhifu/Documents/workspace/tomato-platform/tomato-module/tomato-module-merchant/tomato-module-merchant-biz

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
