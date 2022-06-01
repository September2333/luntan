## 个人luntan开发记录

1、引入bootstrap 3 实现简单布局
- 加入导航栏：https://v3.bootcss.com/components/#navbar


2、实现github登录
- Creating an OAuth App
- 实现github登录参考文档：https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps
- AuthorizeController,实现具体登录
    luntan携带code请求access_token：参考https://square.github.io/okhttp/
    使用okhttp用post方式
- 配置cookie、session
  通过thymeleaf引擎，在index.html中，设置登录后显示session获取的用户名，即通过写入session的方式记录当前登录态
- 整合mybatis
  使用MySQL创建用户表，并实现登录信息写入数据库
  用户账户通过UUID生成的token实现绑定前端后端登录状态
- 实现持久化登录状态获取