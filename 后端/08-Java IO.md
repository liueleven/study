## Java IO

更新于2018-07-22

> 抽象：java核心思想，将任何复杂的细节操作，都隐藏起来——流、文件

#### 什么是Java IO？
> IO即Input和Output，输入输出系统，只要想和硬盘打交道，那都得走这两条通道

#### 常用的类
- InputStream
    > 对字节的操作，所有输入流的爸爸，它名下有几个常用的子类：FileInputStream（文件的操作），ByteArrayInputStream（字节数组的操作）等
- OutputStream
    > 对字节的操作，所有输出流的爸爸，它名下有几个常用的子类：FileIOutputStream（文件的操作），ByteArrayOutputStream（字节数组的操作）等
- Writer
    > 字符的输出,它名下有几个常用的子类：BufferdReader
- Reader
    > 字符的输入,它名下有几个常用的子类：BufferdWriter
- File
    > 是一个对文件操作的工具类

#### 字节还是字符？
- 字节
    > 字节=byte=0000 1111，所有的文件本质都是用字节进行存储的，非文本用字节进行操作
- 字符
   > 字符是对字节的抽象。在java中，用的Unicode编码，一个字符=2个字节。ANSI编码中，标点符号、数字，字母占一个字节，一个汉字2个字节。一般对文本操作就用字符

#### InputStream的操作
> InputStream中有一个基本的方法int read(); 是读取一个字节，并将读取到的字节返回，如果返回的是-1，那么表示读到末尾了。

#### OutputStream的操作
> OutputStream中有一个基本的方法void write(int b); 是写入一个字节
##### 字符的操作会用到缓存区，将一批字符先保存到缓存区，然后写入到硬盘中
#### Reader的操作
> int read(); 是读取一个字符，并将读取到的字符返回

#### Writer的操作
> void write(int b); 是写入一个字符

#### 字符和字节相互转换
- InputStreamReader：字节到字符
- OutputStreamWriter： 字符到字节

### IO模型
#### BIO 同步阻塞
> 该模式每次请求都会创建一个线程，开销较大，但是稳定
#### NIO 同步非阻塞
> 该模式主要是开启一个线程，进行轮询是否有线程出于就绪状态,JDK1.4以后都有改特性
#### AIO 异步非阻塞
