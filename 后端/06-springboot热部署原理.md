# SpringBoot热部署原理

更新于2018-07-22

超细的官方文档：[猛戳](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/)

## 热部署与热加载 

##### 相同
- 都是基于java的类加载器实现的
- 无需停止服务或程序 

##### 区别
- 热部署直接重新加载整个应用
- 热加载在运行时重新加载class
##### 应用
- 热部署更多的是在生产环境使用
- 热加载更多的是在开发环境使用
- 

## Java类加载器
##### 特点
- 由AppClass Loader（系统类加载器）开始加载指定的类
- 类加载将加载任务交给父加载，如果父没有找到，再由自己去加载
- Bootstrap Loader（启动类加载器）是最顶级的类加载器了

##### 热部署方法
- 把web文件夹放在webapps下
- 在\conf\server.xml中的<host></host>标签添加<context/>标签进行配置
- 还有几种方法（具体可以参考Tomcat官网配置说明）












