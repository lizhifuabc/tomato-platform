# zipkin

https://zipkin.io/

https://github.com/openzipkin/zipkin

https://github.com/openzipkin/zipkin/tree/master/docker

# 快速启动

docker run -d -p 9411:9411 openzipkin/zipkin

启动 Zipkin 镜像的容器，并将容器的端口 9411 映射到主机的端口 9411。Zipkin 是一个分布式跟踪系统，用于监视和排查微服务架构中的请求流。

这个命令的意义是在后台运行一个 Zipkin 容器，并将容器的 9411 端口映射到主机的 9411 端口，以便您可以通过浏览器或其他工具访问 Zipkin Web UI。

要查看 Zipkin 的 Web UI，请在您的浏览器中访问 http://localhost:9411。这将显示 Zipkin 的用户界面，您可以在其中查看跟踪数据和分析请求流。