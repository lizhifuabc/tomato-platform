# mysql
docker-compose -f docker-compose.yml -p mysql8 up -d

使用指定的 Docker Compose 文件配置和启动一个 MySQL 8.0 容器，并将容器置于后台运行。

- `docker-compose -f docker-compose.yml`: 这部分命令指定使用名为 `docker-compose.yml` 的 Docker Compose 文件来配置容器。
- `-p mysql8`: 这部分命令指定为 Docker Compose 项目分配一个名称，这里将项目名称设置为 `mysql8`。
- `up -d`: 这部分命令表示将启动容器并将它们置于后台运行（即“守护模式”）。



查看容器日志:

docker logs mysql8

特定的日志级别:

docker logs -f mysql8
