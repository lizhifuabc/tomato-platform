# Jenkins

https://www.jenkins.io/zh/

[Jenkins](https://www.jenkins.io/)

## 特点

1. 仅仅一个java -jar jenkins.war，从官网下载该文件后，直接运行，无需额外的安装，更无需安装数据库；
2. 提供友好的GUI配置界面；
3. Jenkins能从代码仓库中获取并产生代码更新列表并 输出到编译输出信息中；

## 安装

1. 赋予权限(读、写、执行)：`chmod -R 777 ./jenkins`
2. 启动：`docker-compose -f docker-compose-jenkins.yml -p jenkins up -d`
3. 访问地址：`127.0.0.1:8888`

命令解析：

1. `-f docker-compose-jenkins.yml`: 指定要使用的 Docker Compose 配置文件为 "docker-compose-jenkins.yml"。
2. `-p jenkins`: 指定项目名称为 "jenkins"。这将为项目中的所有容器添加一个前缀，以避免与其他项目的容器冲突。
3. `up -d`: 启动容器。`-d` 参数表示在后台运行容器，而不会阻塞当前终端。

##  查看密码

```shell
# 普通权限进入到docker容器
docker exec -it jenkins /bin/bash
# 使用root权限进入到docker容器
docker exec -it -u root jenkins /bin/bash
# 查看密码:671d33f166e4493ba2083418cdfeb8a9
cat /var/jenkins_home/secrets/initialAdminPassword
```



## 其他

Mac 下 Docker 没有 /var/run/docker.sock 目录：

Mac 安装 docker desktop 客户端后，没有 /var/run/docker.sock 目录的解决方案。 运行以下命令：

```shell
sudo ln -s /Users/lizhifu/.docker/run/docker.sock /var/run/docker.sock
```

