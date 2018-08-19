# Maven构建多模块

#### 看完本文
利用Maven可以构建多模块，打包成jar或者war包

#### 本文须知
有springboot基础，需自己先写好一个基本springboot应用

##### 第一步
创建一个SpringBoot项目，写好controller、server、repository，将这个项目进行拆分，这个项目的pom.xml我们称为父pom.xml

##### 第二步
在项目中分别创建Maven Module,依次对应三个层次，会自动添加到父pom.xml中的，如图：
![image](https://github.com/liueleven/study/blob/master/%E5%9B%BE%E5%BA%93/05-maven/01-maven%E6%9E%84%E5%BB%BA%E5%A4%9A%E6%A8%A1%E5%9D%97-4.png?raw=true)

##### 第三步
由于各个模块之间是互相依赖的，所以要各个模块之间互相添加依赖，例如在web模块中的pom.xml添加service依赖
```
<artifactId>persistence</artifactId>

<dependency>
    <groupId>com.imooc</groupId>
    <artifactId>model</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
 ```
 
##### 第四步
使用maven打包，非常重要,需要把父pom.xml中的配置复制到启动类下的那个pom.xml，这里我的启动类是web模块下，然后再执行maven命令：maven clean package
```
<!--将父pom的这段配置放到web下-->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <mainClass>com.imooc.module.ModuleApplication</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```
##### 第五步
springboot默认打包是jar的形式，如果需要打包成war包，需要在启动类的pom.xml中加上,我这里是web模块
```
<packaging>war</packaging>
```
然后再新建web.xml目录结构
![image](https://github.com/liueleven/study/blob/master/%E5%9B%BE%E5%BA%93/05-maven/01-maven%E6%9E%84%E5%BB%BA%E5%A4%9A%E6%A8%A1%E5%9D%97-5.png?raw=true)




