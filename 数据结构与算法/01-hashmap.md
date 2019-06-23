## 数据结构——HashMap实现原理及应用

更新于2018-07-18
#### 读完本篇，你能获得啥？
- 了解hashmap内部结构和实现原理
- 了解它的优点和不足
- 并发情况下的使用


#### 先来了解下HashMap特性
- HashMap是键值对的方式进行存储的
- HashMap是非线程安全的
- key和value都可以为null值
- 插入慢，获取快

#### 从结构看实现原理
> java中hashmap继承了abstractMap，实现了map接口。hashmap以key-value形式进行存储，我们在用的时候直接map.put(key,value)就可以了，

#### 几个重要的参数
-  初始容量值：创建一个hashmap时的容量，表示该hashmap的大小，默认是16
- 加载因子：load factor is a measure of how full the hash table is allowed to get before its capacity is automatically increased. （翻译水平不是很高，怕误导读者理解），默认是0.75，当这个map到达了总容量的75%那么就会创建新的容量是原来的2倍，并把原来的数据再次插入。这各扩容的过程称为rehashing
####  HashMap原理 
先看看HashMap的内部存储结构
![这里写图片描述](https://s1.ax1x.com/2018/07/17/PlQqm9.png)

HashMap结构是链表和数组的结合体。数组是主体，因此每一个HashMap开始会有一个初始容量值长度为16，会自动扩容，每次扩容的容量是2的幂。数组中存储的是Entry,每一个Entry是一个链表，链表主要是为了解决hashcode重复的问题，当一个key的hashcode重复时，就会追加到这个Entry的链表中。一个Entry由key-value-hash-next组成，其中key-value就是键值对，next是指向下一个Entry。HashMap是基于hash原理的，通过put和get来储存和获取值，当用put时会调用对象的hashCode()方法获取相对应的hashcode，只有key和hash都相同时，才会放到Entry 中。当使用get获取对象时，通过键对象   的equals()方法找到正确的键值对，然后返回值对象。

#### 优点与不足
优点：

- HashMap的查找效率特别高

缺点：
- 插入的效率很差
- 多线程中使用HashMap存在线程安全问题(不建议使用)
#### HashMap面试点
- 为什么String,Interger这样的wrapper类适合作为key？
> 因为包装类是final不可变的，减少了hashcode的碰撞率，提高获取的速度

- 我们可以使用自定义的对象作为键吗？
> 可以，但是你要重写hashcode和equals，并且保证这个对象不可变，否则就取不到该值了

- HashMap中的hashcode值会相同吗？
> 会，它们会储存在同一个Entry位置的链表中。键对象的equals()方法用来找到键值对。

- HashMap中key和value可以为null吗？
> 可以的

-为什么说HashMap是不安全的？
> 在多线程环境下，当容量到达了load factory的尺度，就会扩容，这时当多个线程都进行扩容的时候，每个线程都去计算元素位置，但只有一个线程最终会把原来的数据插入到新的数据，其它的线程就会死循环；另一个情况是当多个线程进行put操作是假如hashcode一致，那么value值就会被覆盖掉



- HashMap扩展——CurrentHashMap原理
> CurrentHashMap是线程安全的，且性能比HashTable好很多，关键是用到了锁分段（segment）技术，即将数据分成很多个片段，多把锁来管理，大大的提高了效率



