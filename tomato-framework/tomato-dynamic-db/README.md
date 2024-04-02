# 动态数据源



```yml
spring:
  dynamic:
    master: master
    enabled: true
    pool:
      master:
        username: root
        password: 123456
        url: XXXX
      slave1:
        username: root
        password: 123456
        url: XXXX
      slave2:
        username: root
        password: 123456
        url: XXXX 
```

