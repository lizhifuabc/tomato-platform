Spring Boot Admin是一个开源的应用监控工具,可以监控Spring Boot应用的各项指标并通过UI展示。
它包含两个部分:
- Admin Server:通过HTTP API和UI展示监控信息
- Admin Client:嵌入在每个Spring Boot应用中,用来收集应用指标并推送给Admin Server

启动应用后,Admin Client会自动注册到Admin Server,并开始推送各项指标,在Admin Server的UI中我们可以看到该应用的监控信息:
- 应用名称、实例ID
- 运行状态(Running/Not Registered)
- JVM信息(内存使用、GC时间等)
- 应用信息(启动时间、运行时长等)
- 度量值(通过Spring Boot Actuator暴露的各项指标)
- 环境信息
- 日志文件(错误日志、审计日志)
  使用Spring Boot Admin,我们可以实现以下目标:
1. 集中监控所有Spring Boot应用的运行状态
2. 快速发现并排查应用出现的问题(通过日志、度量指标)
3. 分析应用的运行时行为,进行Tuning优化
4. 实现基于指标的弹性伸缩