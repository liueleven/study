# shell学习笔记

更新于2018-07-22

### 学习事物的三套路：

1.是什么？

它是用户使用 Linux 的桥梁。Shell 既是一种命令语言，又是一种程序设计语言。
	
2.能干嘛？

使用它可以更好的在linux上工作，提高效率，可以编写脚本，节省时间。

3.怎么用？

直接在linux写好脚本程序，然后sh *.sh或./  *.sh执行就行
	
### 了解下shell
1.shell有很多种格式，但一般shell脚本是以#!/bin/bash开头

2.如何输出：echo 是输出指令；	例如：echo ’我是万能的java‘   -e表示开启转义  echo -e "ok! \n"  这里就会换行

3.如何定义变量：没有特殊的指令来标记，只需注意变量名的规范性即可；获取变量只需在该变量前面加‘$'符合即可；	
	例如：name=’十一‘	echo $name 或者 echo ${name}		注意：最好不要有空格，能在一起就在一起
	
4.readOnly定义的变量不能修改和删除

5.unset 可以删除一个变量	例如：unset ${name}

6.shell中有三种变量类型：

    1.环境变量，所有的shell都共享
    2.shell变量，所有shell共享
    3.局部变量，只在改shell文件中共享
	
7.字符类型操作：

	1.输出字符长度：name='lzq'		echo ${#name}
	2.截取字符串：	name='hello world'	${name:1:4}
	3.查找指定字符串的位置：name='hello world' 	echo `expr index "{$name}" is`
	4.=	判断2个字符串是否相等
	5.!=判断2个字符串是否不相等
	6.-z检查字符长度是否为0
	7.-n检查字符长度是否不为0

8.数组用括号表示，元素之间用空格区分,例如:

    arr=(1 2 3 4 5 )  读取数组中的元素${arr[2]}	获取数组所有元素${arr[@]}
	
9.注释用‘#’键

10.向文件传递参数，在文件中用$n接受，第一个参数就是$1,第二个就是$2....,注意的是第一个参数默认就是文件名,$* 显示所有参数，$$当前进程id,例如：

    sh demo.sh 1 2 3	#这里传了4个参数，分别是demo.sh、1、2、3 
	
11.shell不支持简单的数学运算，但是可以借助expr 表达式来完成+ - * /
	例如：val=·expr 1 + 2· 
	
12.关系运算符，只能是数字
	-eq：是否相等，相等返回true
	-gt：左边的是否大于右边的，大于返回true
	-lt：左边的是否小于右边的，小于返回true
	
13.布尔运算符：！非		-o或	-a与

14.逻辑运算符：&&与		||或

15.流程控制语句

	```
	if：最后是if的倒写fi结尾表示结束
		if condition
		then 
			command1
		elif condition
		then 
			command2
		else
			command3
		fi
	for:
		for var in item1 item2 ... itemN
		do
			command1
		done
	while:
		while(( condition ))
		do
			command
		done
	case 值 in
	模式1)
		command1
		;;
	模式2）
		command1
		;;
	esac
	```
#### 案例一：

重启jar包
```
#!/bin/bash
echo '-------------------------ready operating----------------'
pid=`ps -ef | grep "mge-backend" | grep -v "grep" | awk '{print $2}'`
echo '----------------------------------------pid='$pid
kill -9 $pid
nohup java -jar mge-backend-v1.0.jar >/home/mge-uworks/backend-log.txt &
sleep 5
jps
echo '-------------------------ok!---------'
```
#### 案例二：
从指定路径重启war包
```
#!/bin/bash
echo '--------------开始---------------'
rm -fr /home/*/webapps/ROOT/*
cp /home/*/nuoxin_shipin.war /home/*/webapps/ROOT/
cd /home/*/webapps/ROOT/
jar xvf *.war
rm -f *.war
/home/*/tomcat/bin/shutdown.sh
sleep 5s		
tomcat_pid=`ps -ef|grep nuoxin_shipin |grep -v grep|awk '{print $2}'`	
echo ${tomcat_pid}
[ -n "${tomcat_pid}" ] && kill -9 "${tomcat_pid}" 
/home/*/tomcat/bin/startup.sh
echo '--------------结束---------------'
```

		