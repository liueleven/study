## linux上登不上mysql报错 “Can't connect to local MySQL server through socket '/var/lib/mysql/mysql.sock”


从报错上看这个路径下是没有这个文件/var/lib/mysql/mysql.sock，网上找了很多都说是在配置文件中修改路径，但是我已经误删了这个文件了，最终解决方案是要重新生成文件才可以。命令如下：

> /usr/local/bin/mysql_install_db

> /usr/local/bin/mysqld_safe & 

---
最后会在/var/lib/mysql/路径下重新生成一系列相关文件，然后再登录，Amazing！！！
