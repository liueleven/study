# maven插件 热部署

更新于2018-07-22

##### 在pom.xml中添加以下插件配置

```
<build>
    <plugins>
    	<plugin>
    		<groupId>org.springframework.boot</groupId>
    		<artifactId>spring-boot-maven-plugin</artifactId>
    		<dependencies>
    		    <dependency>
                	<groupId>org.springframework.boot</groupId>
                	<artifactId>spring-boot-devtools</artifactId>
                	<scope>provided</scope>
                	<optional>true</optional>
                </dependency>
            </dependencies>
    	</plugin>
    </plugins>
</build>
```
#### 或者
```
<build>
    <plugins>
    	<plugin>
    		<groupId>org.springframework.boot</groupId>
    		<artifactId>spring-boot-maven-plugin</artifactId>
    		<dependencies>
    		    <dependency>
                	<groupId>org.springframework</groupId>
                	<artifactId>springloaded</artifactId>
                	<version>1.2.6.RELEASE</version>
                </dependency>
            </dependencies>
    	</plugin>
    </plugins>
</build>
```
#### 或者（这个最简单，先试这种）
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
	<scope>provided</scope>
	<optional>true</optional>
</dependency>
```