# 初识Docker

##### 上次更新 2018/8/26
##### 本次更新 2018/8/26

### 谈谈个人理解
先谈谈个人理解，如果你使用过linux，那么使用docker很简单只需记住常用的docker命令就可以，所以不要怕，开干！

### 相关资源
- 网易镜像库[地址](https://c.163.com/hub#/m/home/),每个镜像的使用方式，都会有相应介绍，很方便
- 慕课网的一个入门[视频](https://www.imooc.com/learn/824)
- 
### 什么是Docker？
可以存放应用的容器，集装箱。

简化运维，提供了统一的环境，节省了机器成本

跨平台，支持，win，linux，macos

### docker架构
理解docker的架构，有助于我们更好的使用，出现问题能看快速定位。常见的名词有以下：
- docker images

docker 镜像，镜像其实就是文件，用于创建 Docker 容器的模板，比如我们要在docker安装Tomcat，就要去相应的仓库pull下Tomcat的镜像文件

- docker container

docker容器，容器是独立运行的一个或一组应用，容器的本质是一个进程

- docker registry

docker仓库，仓库用来保存镜像，可以理解为代码控制中的代码仓库，方便其他容器可以访问

- docker host

docker主机，主机是用来执行docker守护进程和容器

- daemon

守护进程


### win7下安装docker

请查看[菜鸟教程](http://www.runoob.com/docker/centos-docker-install.html)

由于Windows的原因只能按照toolbox，但是安装后点击Docker Quickstart Terminal会启动会失败，因为没有关联到这个启动脚本，这是你要找到你的安装目录下有个start.sh的启动脚本，但是本地要保证安装过git，就可以启动成功了，第一次会比较慢。

然后运行docker run hello-world会出现Hello from Docker!说明docker能够跑一个简单的文件了

### 手动创建docker镜像(一)
第一步：touch Dockerfile   //创建文件

第二步：vi Dockerfile  //编辑，输入以下内容
```
FROM alpine:latest  
MAINTAINER xbf  
# 运行命令，输出内容
CMD echo 'hello docker' 
```
第三步：docker build -t hello_docker .【这里是有个点的】 //编译这个文件，-t修改镜像文件名称为hello_docker,点表示当前路径下

第三步：docker images hello_docker //查看该镜像信息

第四步：docker run hello_docker //运行这个镜像文件，会输出hello docker

### 手动创建docker镜像-java web应用jpress（二）
第一步：先去网易镜像库下载Tomcat镜像，因为web是基于Tomcat的，再去下载[Jpress war包](https://gitee.com/fuhai/jpress/tree/alpha/)

第二步：创建文件Dockerfile

第三步：编辑文件
```
# 声明这个是基于Tomcat的
from hub.c.163.com/library/tomcat:latest

# 声明作者之类的
MAINTAINER liueleven liueleven@aliyun.com
# 复制war到Tomcat的目录，
COPY jpress-web-newest.war  /usr/local/tomcat/webapps
```
第四步：docker build -t jpress:latest .  编译并改名称和版本

第五步：docker images 查看镜像

第六步：docker run -d -p 8888:8080 jpress

第七步：访问浏览器 http://118.126.102.69:8888/war包的名称/install

第八步：同理，安装mysql也类似安装Tomcat的步骤
##### 创建docker文件相关命令
```
FROM base image
RUN  执行命令
ADD  添加文件
COPY 拷贝文件
CMD  执行命令
EXPOSE  暴露端口
```

### Volume
独立docker容器之外的持久化存储
### docker常用命令
- docker version 

查看版本信息
- service docker start 

启动docker
- docker run REPOSITORY[:TAG]

用来运行容器，比如docker run nginx。如果没有该模块docker会去下载

docker run -d -p 8080:80  nginx  这一行的命令中，-p是端口映射，将nginx的80端口映射到8080中，-d表示直接返回该应用的container id，后台运行该应用

- docker images

显示本地所有的镜像文件

- docker stop 容器id

停止docker中的某个应用

- docker ps

查看所有运行的容器信息

- docker restart 容器id

重启某个镜像

- docker rm

删除容器

- docker rmi

删除image

- docker cp

用于host和container之间拷贝文件

- docker commit

保存改动一个image，成为一个新的image

- docker pull 应用镜像地址
从仓库中心获取一个应用



