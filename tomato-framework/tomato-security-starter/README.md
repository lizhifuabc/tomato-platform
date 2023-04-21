## 基于 token 的形式进行授权与认证

1. 用户名密码认证成功，获取当前用户角色的一系列权限值，并以用户名为 key，权限列表为 value 的形式存入 redis 缓存中
2. 生成 token 返回，浏览器将 token 记录到 cookie 中，每次调用 api 接口都默认将 token 携带到 header 请求头中
3. 解析 header 头获取 token 信息，解析 token 获取当前用户名，根据用户名就可以从 redis中获取权限列表
4. Spring-security 判断当前请求是否有权限访问。