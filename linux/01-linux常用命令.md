# linux常用命令

更新于2018-07-22
#### 读完本篇，你能获得啥？
- linux常用的操作命令

#### 快捷键
- ctrl + a  回到行首
- ctrl + e  回到行尾
- ctrl + k  删除光标到行尾的字符
- ctrl + l  清屏
#### 查看一些数据
- cat /proc/version
> 查看linux系统版本信息
#### 文件操作
- vi/vim  对一个文件进行编辑，如果没有该文件则创建
> 例如：vi log.txt

- ">"  cat log.txt > log.txt.backup
>定向输出文件
    - 如果该文件存在就将原来的清空，如果不存在就创建

- ">>"  cat log.txt >> log.txt.backup
> 定向追加输出文件,如果该文件存在就将原来的文件末尾追加，如果不存在就创建 

- du -h  
> 查看文件/文件夹大小    
    
- history [num]
> 显示历史命令，num表示显示多少条

- cat filename
> 显示该文件全部内容，cat f1 f2 > f3 将f1，f2文件合并到f3
- touch filename
> 创建一个新的文件

- tail [选项] filename
> 默认显示该文件最后10行
    - tail -f filename 实时刷新
    - tail +num filename 从第num行开始显示
    - tail -num filename 从倒数num行开始显示

- :s/well/good/g 
> vi中替换文字，全局替换，将所有的well替换成good

- find / -name "*名称*"
> 查询所有该名称的文件
    

#### 网页下载
- curl http://www.baidu.com
> 获取网页内容
- curl -O http://***
>下载文件


- wget  wget http://***
> 用于下载
  
#### 系统操作
- top 
> 查看系统cpu、内存等使用情况

- vmstat 1 
> 每秒刷新CPU、IO、memory相关信息

    
- iostat查看io读写
    - 数字  表示多少s刷新一次
- ps -ef | grep 关键字
> 查看是否存在该关键字相关进程


- netstat -apn
> 查看所有应用的ip，端口和状态

- source /etc/profile
> 重新加载系统配置文件，一般修改了里面的配置需要使用该命令

- ifconfig
> 查看本地ip配置信息

#### 查找
- find [目录] -iname '关键字'
> 在该目录中找到该关键字忽略大小写所有信息

#### 压缩与解压
- zip *.zip file1 file2...
> 将众多文件压缩成一个
- upzip *.zip
> 解压文件
- tar -xzvf *.tar
> 解压该文件并列出详细信息

#### 登录与登出
- ssh 用户名@ip
> 远程登录
- logout
> 退出
- shutdown
> 关机

#### 权限操作
```
r=读取属性      值＝4  
w=写入属性　　  值＝2      
x=执行属性　　  值＝1
```
- chmod 777 文件夹或文件
> 授权这个文件夹或文件读写执行的权限