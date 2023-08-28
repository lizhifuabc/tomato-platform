# Docker

有些时候我们需要在本地搭开发环境，包括 MySQL、Redis、Nginx、MQ 、Elasticsearch等等，而且有时候安装会遇到各种各样的问题，这个时候通过 Docker 可以快速搭建和卸载环境。

## 基本原理

Docker 使用 Google 公司推出的 Go 语言进行开发实现，基于 Linux 内核的cgroup，namespace，以及 OverlayFS类的 Union FS 等技术，对进程进行封装隔离，属于操作系统层面的虚拟化技术。由于隔离的进程独立于宿主和其它的隔离的进程，因此也称其为容器。

## 基础命令

```shell
# docker 版本
docker -v
# 获取镜像
docker pull 镜像名称
# 查看镜像列表
docker image ls
# 查看容器
docker ps -a
# 新建并启动容器
docker run -t -i ubuntu:12.04  /bin/bash
# 进入容器
docker exec -it 容器id bash
```

## Docker Desktop

Docker Desktop 官网[Docker: Accelerated Container Application Development](https://www.docker.com/)

设置国内镜像源：

```shell
"registry-mirrors": [
    "https://docker.mirrors.ustc.edu.cn",
    "https://cr.console.aliyun.com/"
  ]
```

## 安装服务

输入关键词：PostgreSQL、Nginx、MYSQL 等等（一般都是找第一个，也就是下载量最大）。

可以 `pull` 或者 `run`，pull 是拉取镜像，run 是拉取镜像+启动容器。

1. **设置镜像名称**：容器名称（Container name），方便区分。
2. **设置端口映射（Ports）**：例如将本机的 `15432`和容器的 `5432`端口绑定，之后就可以用 `15432`做连接端口了
3. **目录映射（Volumes）**：很多服务都会用到存储目录，但是容器本身就在宿主机上，所以需要将服务在容器内的目录映射到宿主机的目录上。
4. **环境变量**：一个服务启动可能会用到启动变量，这些变量可以通过环境变量的方式进行配置。

另外，还可以在容器详情中查看容器的内部文件、状态、配置信息、日志等，以及进入命令行。

## 常见问题

1. 文件无法挂载：sudo chmod -R 777  /usr/local/docker-volumes 
2. 文件无法挂载：docker->preferences->resource->file sharing->添加文件夹

