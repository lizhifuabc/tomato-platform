# 基础镜像 使用alibabadragonwell/dragonwell:17-anolis
FROM alibabadragonwell/dragonwell:17-anolis

# 作者
MAINTAINER lizhifu <lizhifuabc@163.com>

# 定义了构建参数，可以在构建命令中传递这些参数，用于设置环境变量。
ARG VERSION
ARG SERVICE_NAME
ARG SERVICE_PORT

# 将构建参数的值赋给环境变量
ENV VERSION=$VERSION
ENV SERVICE_NAME=$SERVICE_NAME
ENV SERVICE_PORT=$SERVICE_PORT

# 设置容器内的系统编码为 UTF-8
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

# 设置一个空的环境变量，用于存储 Java 运行参数
ENV JAVA_OPTS=""

# 声明了一个挂载点，将容器内的 /tmp 路径映射到宿主机的某个文件夹。
VOLUME /tmp

# 复制构建上下文中的 Spring Boot 应用程序 JAR 文件到容器内，并将它重命名为 app.jar
ADD ./target/${SERVICE_NAME}-${VERSION}.jar app.jar

# 声明运行时容器将监听的端口号，使用了构建参数 SERVICE_PORT
EXPOSE ${SERVICE_PORT}

# 定义容器启动时的入口点命令，运行 Java 应用程序的命令，包括之前设置的环境变量和参数。
# 设置为 /dev/./urandom，以提高随机数生成的性能
# JAVA_OPTS 来自定义 Java 运行时选项和参数，比如内存大小、JMX 监控端口等
# 设置为 /app.jar，即上一步复制的 jar 文件
# 设置为 -Dfile.encoding=utf-8，以解决中文乱码问题
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar -Dfile.encoding=utf-8 /app.jar