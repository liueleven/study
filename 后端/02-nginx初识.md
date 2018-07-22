# Nginx初识

更新于2018-07-22

---
重要的事情先声明：如果你要配置或修改nginx的相关文件，请一定！一定！一定！先**备份**该文件，不然鬼知道会出现什么情况...

--- 
### 前言
先谈谈我对nginx的理解，首先它是一个很优秀的中间件，很多人都这样说。但是到底优秀在哪里，为什么这么会这么优秀，我觉得带着这2个问题去学习，效果会更好！那为什么这么优秀？是因为是它效率高，为什么效率高？说大了就是整个架构，往小的说就是对请求处理的更快，这一系列的为什么一直问下去，第一个大问题就应该可以解决了。第二个优秀的原因是因为它是由很多的模块组成的，也就是说你想要什么功能就可对应的配置相应的模块。
### nginx是一个优秀的中间件
可以做为web服务器，也可以做邮件服务器

IO多路复用（这里的复用指的是线程）,可以在一个线程内并发交替完成操作

基于epoll模型要比select模型好，select是采用轮询的方式，并且有监听数量限制，epoll是采用回调处理。


在处理静态文件要比其他服务器快很多

采用异步非阻塞的方式来处理请求
- 异步非阻塞
> 发送一个事件请求处理，如果没有处理好会跟你说下，等下再来，这期间可以干其他的活
- 异步阻塞
> 发送一个事件请求处理，如果没有处理好，会一直等，直到请求处理完成


### 安装nginx
- [进入官网](http://nginx.org/)
- 找到download，选择Stable version（稳定版）
- 打开文件 vi /etc/yum.repos.d/nginx.repo，复制下面的进行替换

    OS：linux的系统，比如centos，red hat，OSRELEASE: 改系统的版本，比如 7,4
    ```
    [nginx]
    name=nginx repo
    baseurl=http://nginx.org/packages/OS/OSRELEASE/$basearch/
    gpgcheck=0
    enabled=1
    ```
-  执行命令：yum list | grep nginx
-  yum install nginx
-  查看nginx版本：nginx -v
-  查看安装目录：rpm -ql nginx，模块、命令、文档...相关目录都会显示出来
    - /etc 下是nginx的配置目录所在地
        - /logrotate.d/nginx 配置nginx的日志 
        - /nginx/mime.types 配置http中content-type对应关系
        - /nginx/modules nginx模块目录
    - /usr 下是配置系统守护进程管理器模式
        - /lib64/nginx/modules nginx模块目录
    - /var
        -   /cache/nginx  缓存目录
        -   /log/nginx  日志存储目录

### nginx.conf配置详解
```
#$开头是变量  
#定义Nginx运行的用户和用户组  
user work work;  
#nginx进程数，建议设置为等于CPU总核心数  
worker_processes  auto;  
#指当一个nginx进程打开的最多文件描述符数目  
worker_rlimit_nofile 204800;  
#全局错误日志定义类型，[ debug | info | notice | warn | error | crit ]  
error_log  /opt/log/nginx/error.log error;  
#工作模式及连接数上限  
events {  
        #参考事件模型，use [ kqueue | rtsig | epoll | /dev/poll | select | poll ];   
        #epoll模型是Linux 2.6以上版本内核中的高性能网络I/O模型，如果跑在FreeBSD上面，就用kqueue模型  
        use epoll;  
        #单个后台worker process进程的最大并发链接数  
        worker_connections  102400;  
}  
#设定http服务器，利用它的反向代理功能提供负载均衡支持  
http {  
        #文件扩展名与文件类型映射表  
        include       mime.types;  
        #默认文件类型  
        default_type  application/octet-stream;  
        #默认编码  
        charset utf-8;  
        #设定日志格式  
        #log_format  main  '$idXXXX\t$remote_addr\t$remote_user\t$time_local\t$http_host\t$request\t'  
        #             '$status\t$body_bytes_sent\t$http_referer\t'  
        #             '$http_user_agent\t$http_x_forwarded_for\t$request_time\t$upstream_response_time\t$userid';  
        log_format main "$cookie_idXXXX\t$remote_addr\t$remote_user\t[$time_local]\t$request_method\t$host\t$request_uri\t"  
                        "$request_time\t$status\t$body_bytes_sent\t'$http_referer'\t"  
                        "'$http_user_agent'\t'$http_x_forwarded_for'\t$upstream_addr\t$upstream_response_time\t$upstream_status\t";  
                   
    #不可见字符分隔日志格式  
    #include other_log_format.conf;  
    #实时日志收集json格式日志  
    #include json_log_format.conf;  
  
        #日志流格式  
        log_format stream_log "$cookie_idXXXX\t$remote_addr\t$remote_user\t[$time_local]\t$request_method\t$host\t$request_uri\t"  
                      "$request_time\t$status\t$body_bytes_sent\t'$http_referer'\t"  
                      "'$http_user_agent'\t'$http_x_forwarded_for'\t$upstream_addr\t$upstream_response_time\t3";  
          
        #成功日志  
        access_log  /opt/log/nginx/access.log main;  
        #access_log syslog:local6:notice:log1.op.XXXXdns.org:514:nginx-main-log main;  
          
        #指定 nginx 是否调用 sendfile 函数（zero copy 方式）来输出文件，对于普通应用，  
        #必须设为 on,如果用来进行下载等应用磁盘IO重负载应用，可设置为 off，以平衡磁盘与网络I/O处理速度，降低系统的uptime.  
        sendfile        on;  
        #长连接超时时间，单位是秒  
        keepalive_timeout  60;  
        #服务器名称哈希表的最大值(默认512)[hash%size]  
        server_names_hash_max_size 1024;  
        #服务器名字的hash表大小  
        server_names_hash_bucket_size 256;  
        #客户请求头缓冲大小   
        client_header_buffer_size 4k;  
        #如果header过大，它会使用large_client_header_buffers来读取  
        large_client_header_buffers 4 256k;  
        client_header_timeout  1m;  
        client_body_timeout    1m;  
        send_timeout           1m;  
        #防止网络阻塞  
        tcp_nopush     on;  
        tcp_nodelay    on;  
        #允许客户端请求的最大单文件字节数  
        client_max_body_size 50m;  
          
        #缓冲区代理缓冲用户端请求的最大字节数  
        client_body_buffer_size 50m;  
          
        #nginx跟后端服务器连接超时时间(代理连接超时)  
        proxy_connect_timeout 5;  
          
        #后端服务器数据回传时间(代理发送超时)  
        proxy_send_timeout 15;  
          
        #连接成功后，后端服务器响应时间(代理接收超时)  
        proxy_read_timeout 15;  
          
        #设置代理服务器（nginx）保存用户头信息的缓冲区大小  
        proxy_buffer_size 4k;  
          
        #proxy_buffers缓冲区，网页平均在32k以下的话，这样设置  
        proxy_buffers 8 32k;  
          
        #高负荷下缓冲大小（proxy_buffers*2）  
        proxy_busy_buffers_size 64k;  
          
        #设定缓存文件夹大小，大于这个值，将从upstream服务器传  
        proxy_temp_file_write_size 64k;  
        proxy_intercept_errors  on;  
        #客户端放弃请求，nginx也放弃对后端的请求  
        #proxy_ignore_client_abort on;  
  
    #代理缓存头信息最大长度[设置头部哈希表的最大值，不能小于你后端服务器设置的头部总数]  
    proxy_headers_hash_max_size 512;  
    #设置头部哈希表大小(默认64)[这将限制头部字段名称的长度大小，如果你使用超过64个字符的头部名可以加大这个值。]  
    proxy_headers_hash_bucket_size 256;  
  
    #变量哈希表的最大值(默认值)  
    variables_hash_max_size 512;  
    #为变量哈希表制定关键字栏的大小(默认64)  
    variables_hash_bucket_size 128;  
          
        #开启gzip压缩输出  
        gzip on;  
        #最小压缩文件大小  
        gzip_min_length 1k;  
        #压缩缓冲区  
        gzip_buffers     4 16k;  
        #压缩等级  
        gzip_comp_level 9;  
        #压缩版本（默认1.1，前端如果是squid2.5请使用1.0）  
        gzip_http_version 1.0;  
        #压缩类型，默认就已经包含textml  
        gzip_types text/plain application/x-javascript application/json application/javascript text/css application/xml text/javascript image/gif image/png;  
        gzip_vary on;  
  
        #map模块使用  
        map_hash_max_size 102400;  
        map_hash_bucket_size  256;  
  
        #Tengine Config  
        #concat on;  
        #trim on;  
        #trim_css off;  
        #trim_js off;  
        server_tokens off;  
        #footer "<!-- $remote_addr $server_addr $upstream_addr -->";  
  
        #rewrite_log on;  
        fastcgi_intercept_errors on;  
    #include other config file  
        include ../conf.d/*.conf;  
    #包含一些特殊站点的配置文件,此目录下文件暂时不包含在git自动管理过程中  
    include ../special/*.conf;  
  
    #屏蔽不加主机域名的默认请求  
    #server {  
        #   listen *:80 default;  
        #   server_name _ "";  
        #   return 444;  
    #}  
      
    #ceshi by dongange 2016-01-07  
  
    #Nginx状态监测模块配置  
    req_status_zone server "$server_name,$server_addr:$server_port" 10M;  
    req_status server;  
    server {  
        listen 127.0.0.1:80;  
        server_name 127.0.0.1;  
        access_log /opt/log/nginx/nginx_status/status_access.log main;  
            location /status {  
                req_status_show;  
                access_log /opt/log/nginx/nginx_status/status_access.log main;  
                allow 127.0.0.1;  
                deny all;  
                }  
            location /stub_status {  
                stub_status on;  
                access_log /opt/log/nginx/nginx_status_stub/status_stub_access.log main;  
                allow 127.0.0.1;  
                deny all;  
            }  
            location /check_status {  
                check_status;  
                access_log /opt/log/nginx/nginx_status_check/status_access_check.log main;  
                allow 127.0.0.1;  
                deny all;  
            }  
    }  
}  
```
### nginx中location配置
> 这个配置是最基础的了，用来配置请求的url处理，可以配置多个location规则，默认是从上到下开始进行匹配的，匹配成功就直接进行操作

语法：
```
 location [=|~|~*|^~|/] /uri/ { 匹配成功后的操作 }
 ```
 - = 表示精确匹配
 - ~ 开头表示区分大小写的正则匹配
 - ~*  开头表示不区分大小写的正则匹配
 - ^~ 开头表示uri以某个常规字符串开头
 - / 通用匹配，任何请求都会匹配到
##### 这篇文章对location配置介绍的比较全[建议阅读](https://www.cnblogs.com/sign-ptk/p/6723048.html)
### nginx进程模型
- nginx默认是以多进程的方式进行工作的，同时也是支持多线程的
    > nginx在启动后会有一个master进程，和多个worker进程，master主要是管理worker的，worker用来处理请求，worker的数量是可以设置的。一般设置成和cpu核数一致  （很重要的原因是如果进程数大于cpu核数的话，进程会抢占cpu资源，从而带来了不必要的上下文切换）。

### nginx模块
nginx -V 查看信息
```
--prefix=/etc/nginx 
--sbin-path=/usr/sbin/nginx
--modules-path=/usr/lib64/nginx/modules
--conf-path=/etc/nginx/nginx.conf
--error-log-path=/var/log/nginx/error.log
--http-log-path=/var/log/nginx/access.log 
--pid-path=/var/run/nginx.pid 
--lock-path=/var/run/nginx.lock
--http-client-body-temp-path=/var/cache/nginx/client_temp
--http-proxy-temp-path=/var/cache/nginx/proxy_temp
--http-fastcgi-temp-path=/var/cache/nginx/fastcgi_temp
--http-uwsgi-temp-path=/var/cache/nginx/uwsgi_temp
--http-scgi-temp-path=/var/cache/nginx/scgi_temp 
--user=nginx 
--group=nginx 
--with-compat 
--with-file-aio 
--with-threads 
--with-http_addition_module 
--with-http_auth_request_module 
--with-http_dav_module 
--with-http_flv_module 
--with-http_gunzip_module 
--with-http_gzip_static_module 
--with-http_mp4_module 
--with-http_random_index_module 
--with-http_realip_module 
--with-http_secure_link_module 
--with-http_slice_module 
--with-http_ssl_module 
--with-http_stub_status_module 
--with-http_sub_module          //nginx当前连接的状态信息
--with-http_v2_module 
--with-mail 
--with-mail_ssl_module 
--with-stream 
--with-stream_realip_module 
--with-stream_ssl_module 
--with-stream_ssl_preread_module 
--with-cc-opt='-O2 -g -pipe -Wall -Wp,-D_FORTIFY_SOURCE=2 -fexceptions -fstack-protector-strong 
--param=ssp-buffer-size=4 -grecord-gcc-switches -m64 -mtune=generic -fPIC' 
--with-ld-opt='-Wl,-z,relro -Wl,-z,now -pie'

```
每个模块都有相关的配置语法和说明，具体可以百度，一般有这三个参数
```
Syntax:  random_index on | off;   //配置语法 
Default: off                  //默认情况
Context: location   //上下文（我理解的是作用域）
//例如：配置--with-http_random_index_module 这个模块，当访问domain/的时候可以随机出现指定目录下的页面
 location / {
        root  /opt/app/code;    #设置一个目录，该目录下面有好几个页面
        random_index on;        #on 表示开启随机功能
    }
```
举一反三，官网也给出了random_index的[Example](http://nginx.org/en/docs/http/ngx_http_random_index_module.html)


### nginx事件模型
略

### 使用过程中遇到的问题总结
- nginx -tc nginx.conf 提示找不到nginx命令
> 如果要使用nginx命令是要在/etc/profile配置的，跟jdk配置一样，指定下安装路径就可以了

- nginx.conf配置文件中每一行都要用分号“;”结尾，细心一点

### nginx常用命令
- kill -HUP pid
> 从容地重启，服务不会中断。首先会去重新加载配置文件，master会开启新的worker线程进行工作，而老的worker则不再接受命令，直到当前任务处理完，自动退出。
- 重启nginx，加载新的配置文件平滑启动，服务不中断
```
kill -QUIT `cat /var/run/nginx.pid `
```
- nginx -s reload
> 重启nginx
- nginx -s stop
> 停止nginx进行
- 
start nginx 启动nginx

nginx -t 检查配置是否正确
nginx -c 指定一个配置文件路径

nginx -s reload 重新载入配置

nginx.exe -s stop   停掉nginx
systemctl restart nginx.service 重启nginx

nginx -V 查看nginx的编译参数

kill -9 nginx   强制停止

nginx -s reload -c /路径/nginx.conf 重新载入指定路径的配置文件

### 配置前端静态文件（案例）
1.安装好nginx，找到nginx的路径，我的是在 /etc/nginx下，假设你已经启动nginx了

2.在conf.d目录下新建一个文件进行配置，test.conf
```
server{
        listen 9999;
        server_name localhost;  //这里有域名就更好了localhost->域名
        location / {
                root /home/**/manage;  //静态文件的路径
        }
}
```

3.在/etc/nginx/nginx.conf中的http{}中引入我们自定义的配置文件：
```
http {
    ...
    include /etc/nginx/conf.d/*.conf;
}


```


4.运行nginx -s reload 重新加载配置文件，访问localhost:9999顺利的话就可以了

#### 参考博客
https://www.cnblogs.com/h9527/p/5530298.html

http://www.cnblogs.com/hanyinglong/p/5141504.html