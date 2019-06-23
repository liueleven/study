# ES6小结

先看大佬的的ES6入门[阮一峰](http://es6.ruanyifeng.com/#docs/intro)
> ECMAScript 是js的一个规范标准，现在已经第6个版本了，最近学习Vue，遇到了一些ES的语法，特此记录一波

#### 关键字let
- 1、被let关键修饰的变量，作用域只在块中，下面是var和let的对比：
```
    {
        var name = 'zhangsan';
        console.log(name);      //输出  zhangsan
        let salary = 200;
        console.log(salary);    //输出  200
    }
    console.log(name);      //输出  zhangsan
    console.log(salary);    //报错  Uncaught ReferenceError: salary is not defined
```
- 2、被let关键修饰的变量，作用域不会被提升：
```
    {
        console.log(name);      //输出  lisi
        var name = "lisi";      
        console.log(salary);    //报错  Uncaught ReferenceError: salary is not defined
        let salary = 300;       
    }
```
- 3、被let修饰的变量，不能重复声明
```
{
        var name = 'zhangsan'
        var name = 'lisi'
        console.log(name);      
        let salary = 200;
        let salary = 300;
        console.log(salary);     // 解析时就报错 Uncaught SyntaxError: Identifier 'salary' has already been declared
    }
```
#### 关键字const
修饰常量，特性同let的1、2、3点，还要注意的是const声明时要初始化，被const修饰的常量不能改变

#### 对象
对象中是以key-value来写的，es6中如果key和value一样的话可以简写：
```
    // 原来的写法
    var name = "zhangsan";
    var age = 23;
    var obj = {
        "name": name,
        "age":age
    }
    console.log(obj);
    // es6的写法
    let obj2 = {
        name,       //等价于"name":name
        age
    }
    console.log(obj2);
```
#### 扩展运算符 ...
es6中“...”这三个点叫做扩展运算符，其实根据它的实际用法，叫做**展开运算符**会更好理解：
```
    let arr = [2,"baidu","zhihu",2,"alibaba","baidu"]
    console.log(...arr);  // 输出 2 "baidu" "zhihu" 2 "alibaba" "baidu"
```
也就是可以把数组所有元素全部展开，也支持字符串，但是不支持对象。看个实用的例子：**去掉数组中重复的值**
```
    let arr = [1,2,3,"baidu","zhihu",2,"alibaba","baidu"]
    console.log([...new Set(arr)]);  // 输出[1, 2, 3, "baidu", "zhihu", "alibaba"]
```
#### 箭头函数
>  箭头函数的特点是简洁，灵活，还有就是this的指向问题,可以参考[廖雪峰](https://www.liaoxuefeng.com/wiki/001434446689867b27157e896e74d51a89c25cc8b43bdb3000/001438565969057627e5435793645b7acaee3b6869d1374000)
- 常规函数写法：
    function func1 () {}
- 箭头函数写法：
    () => {}
```
    let num = 2;
    // 常规写法
    let func1 = function (num) {
        return num；
    }
    func1();
    // 箭头函数 如果参数只有一个()是可以省略的，如果一条语句{}也是可以省略的
    let func2 = (num) => {num}  //  let func2 = num => num
    func2();
```