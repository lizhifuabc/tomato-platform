# 基础镜像 使用alibabadragonwell/dragonwell:17-anolis
FROM alibabadragonwell/dragonwell:17-anolis
# 在容器内创建目录。
RUN mkdir -p /tomato-module-notice-start
# 切换工作目录
WORKDIR /tomato-module-notice-start
# 指定应用程序的 JAR 文件路径
ARG JAR_FILE=target/tomato-module-notice-start.jar
# 将构建上下文中的应用程序 JAR 文件复制到容器内的 app.jar
COPY ${JAR_FILE} app.jar
# 声明容器将监听的端口号为
EXPOSE 9996
# 设置为 /dev/./urandom，以提高随机数生成的性能
ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

# 容器启动后,先睡眠3秒钟
# 然后再执行java命令启动应用
CMD sleep 3; java $JAVA_OPTS -jar app.jar
