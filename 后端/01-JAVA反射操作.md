## JAVA反射操作

更新于2018-07-22

- 先来看一道经典面试题：
> 在一个方法中交换2个Integer变量的值，例如Integer i1=28,i2=45;输出结果i1=45,i2=28；
-
-
-
- 先
-
-

- 思


-
-
- 考
-
-
-
-













- 通过JAVA的反射机制可以获取一个类的私有属性进行更改，具体看代码：
```
//Food类
public class Food {
    //私有属性
    private String fruit;

    public String getFruit() {
        return fruit;
    }
}

public static void main(String[] args) throws Exception {
    Food food = new Food();
    Field field = Food.class.getDeclaredField("fruit");//获取该对象的私有属性fruit
    field.setAccessible(true); //可以访问修改
    field.set(food,"香蕉");//将该属性进行修改
    System.out.println(food.getFruit());//控制台输出：香蕉
}

```
- 再回到刚才拿到题目，哦！原来这么简单，啪啪啪，撸了一串代码：
```
    public static void main(String[] args) throws Exception {
        Integer i1=45,i2=28;
        System.out.println("修改前：i1："+i1+"    i2:"+i2);
        swap(i1,i2);
        System.out.println("修改后：i1："+i1+"    i2:"+i2);//输出结果i1=28,i2=28(懵了一逼~~)
    }
    //交换变量值
    static void swap(Integer i1,Integer i2) throws Exception {
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        int temp = i1.intValue();
        field.set(i1,i2.intValue());
        field.set(i2,temp);
    }
```
- 经过一系列的博客点博客、博客转载博客，博客再点博客，终于找到答案：
    >Integer.valueOf()说：“我会缓存请求频繁的值，当你定义的一个值在-127~128之间，为了时间和空间性，如果缓存中有这个值我会直接返回”
    **程序员不干了，还能不能好好说点人话？（键盘一扔，你来敲！！！）**
```
    Integer i1=45,i2=28;
    Integer i3=45,i4=129,i5=129;
    System.out.println(i1==i2);//false
    System.out.println(i1==i3);//true
    System.out.println(i4==i5);//false
```
- 哦~sugar 再次高潮，啪啪啪，又撸了一串代码：
```
    public static void main(String[] args) throws Exception {
        Integer i1=45,i2=28;
        System.out.println("修改前：i1："+i1+"    i2:"+i2);
        swap(i1,i2);
        System.out.println("修改后：i1："+i1+"    i2:"+i2);//输出结果i1=28,i2=45
    }
    static void swap(Integer i1,Integer i2) throws Exception {
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        int temp = i1.intValue();
        field.set(i1,i2.intValue());
        field.set(i2,new Integer(temp));//new一个新的地址，让它不在缓存中找了
    }
```