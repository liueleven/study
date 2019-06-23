# thymeleaf使用总结
> springboot默认推荐的模板渲染，类似jsp，学习零成本
在springboot中的配置
```
#########thymeleaf config############
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.encoding=UTF-8
#使用这个模式，需要添加nekohtml
spring.thymeleaf.mode=LEGACYHTML5
spring.thymeleaf.cache=false
#静态文件
spring.mvc.static-path-pattern=/static/**
```
添加依赖
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
thymeleaf使用html5,且非常严格，有些则直接解析不了，所以一般还会添加nekohtml对部分标签进行补充
```
<dependency>
	<groupId>net.sourceforge.nekohtml</groupId>
	<artifactId>nekohtml</artifactId>
</dependency>
```

#### thymeleaf中使用&进行get请求拼接会报错
```
需要将 & 换成 &amp; 但是这样后端是转义不了的，所以找个方法不行
//<![CDATA[   这里写js代码   //]]>
可以用这个把js代码包裹起来，这样使用 & 就不会解析出错了
```

#### th:text，th:utext和th:value的区别？
```
th:text=""直接将内容显示在这个标签中
th:utext=""如果该内容被html标签包裹，会去除html标签
th:value=""有value属性就会显示，比如input
```
#### 如何给js函数传参？
> 很多时候需要出发点击事件之类的，折后需要从页面传值到js进行处理
##### 方法一：
```
//在html中传入
<a href="#" onclick="findCompetition(this)" th:id="${game.id}"></a>
```
在这里th:id相当于绑定了一个属性，它的值是${game.id}，然后再js中获取，这种可以绑定html的属性，容易混淆，不建议使用
```
<script>
var findCompetition = function (obj) {
    var gameId = $(obj).attr("id"); //获取在html中传入的id值
}
</script>
```
##### 方法二：
> 事件类型的传参，使用这种方式
```
//html
//该单引号就单引号，双引号就双引号，别弄错了（建议直接复制替换），参数前面是\'',后面是'\'
<a th:onclick="'applyTeam(\''+${team.id}+'\',\''+${team.creator.id}+'\')'"></a>

//js
var applyTeam = function(teamid,userId){
    ...
}
```

#### 日期如何转换？
```
   申请日期： <span th:text="${#dates.format(comp.applyTime,'yyyy-MM-dd')}"></span>
```

<div class="tab-pane active" id="hasData" th:if="${#lists.isEmpty(competitionList)}">

#### 如何判断集合为空？
> [thymeleaf官网](https://www.thymeleaf.org/documentation.html) 官方文档找到对应的版本直接搜API即可
```
<div class="tab-pane active" th:if="${#lists.isEmpty(competitionList)}">
    ...
</div>
```
#lists相当于是一个工具类，有很多的方法，isEmpty就是其中用来判断是否为空的

#### js中如何使用后端传入的值？
```
在<script th:inline="javascript"></script>标签中加上th:inline="javascript"，然后对要使用的值加上[[]]
例如：[[${gameList}]]就可以在js中使用gameList这个值了
```

#### html中如何遍历？
> 如下：遍历所有图片
```
<div class="swiper-slide" th:each="banner:${bannerList}">
    <a th:href="@{${banner.url}}">
        <img th:src="@{${banner.img}}" />
    </a>
</div>
```
> 如果要得到下标，bannerStat是一个状态变量，里面可以点出好多，包括下标
```
<div class="swiper-slide" th:each="banner,bannerStat:${bannerList}">
    <a th:href="@{${banner.url}}">
        <img th:src="@{${banner.img}}" />
    </a>
</div>
```
#### 条件表达式
> 动态添加样式
```
　th:class="${time > #dates.createNow()}?'active'"
```
还有其他方法，就不一 一举例了，我比较喜欢这种，简单明了，后续维护也方便

#### th:include代码片段使用
> thymeleaf可以使用th:fragment="该片段的名称"来声明一个代码片段，然后用th:include="fragmentName或者dom选择器"达到复用的功能。比如：
```
//先在一个html文件中声明一个片段
<div id="testHeader">
	<h1>我要放在最前面</h1>
</div>
<div th:fragment="testFooter">
	<h1>我要放在最后面</h1>
</div>
//在pages目录下的另外一个html文件使用它们
<div th:include="pages/test::#testHeader"></div>
<div th:include="pages/test::testFooter"></div>
```
但是建议用fragment更好，因为可以传递参数。例如:
```
//定义一个片段
<div th:fragment="testParams(var1)">
    <h1 th:text="${var1}"></h1>
</div>
//使用该片段
<div th:include="pages/test :: testParams('参数传递测试')"></div>
```
th:include还是很灵活的还可以使用表达式。例如：

```
<div th:include="pages/test :: (5>2? 'testFooter' : '#testHeader')"></div>
```

