# JVM总结

记录自己关于JVM日常学习和遇到的问题

![image]([https://raw.githubusercontent.com/liueleven/study/master/%E5%9B%BE%E5%BA%93/22-jvm/10-JVM%E6%80%BB%E7%BB%93.png](https://raw.githubusercontent.com/liueleven/study/master/图库/22-jvm/10-JVM总结.png))



## 图片中的GC日志分析

### GC日志1

日志内容：

```html
2019-07-14T14:45:37.987+0800: 151.126: 

  [GC (Allocation Failure) 151.126:

​    [DefNew: 629119K->69888K(629120K), 0.0584157 secs]

​    1619346K->1273247K(2027264K), 0.0585007 secs] 

  [Times: user=0.06 sys=0.00, real=0.06 secs]

---------------------------------------------------------------------------
2019-07-14T14:45:59.690+0800: 172.829: 

  [GC (Allocation Failure) 172.829: 

​    [DefNew: 629120K->629120K(629120K), 0.0000372 secs]

​    172.829: [Tenured: 1203359K->755802K(1398144K), 0.1855567 secs]

​    1832479K->755802K(2027264K),

​    [Metaspace: 6741K->6741K(1056768K)], 0.1856954 secs]

  [Times: user=0.18 sys=0.00, real=0.18 secs]
```



日志分析：

```html
上面的 GC 日志暴露了 JVM 中的一些信息。事实上，这个日志片段中发生了 2 次垃圾收集事件(Garbage Collection events)。其中一次清理的是年轻代(Young generation), 而第二次处理的是整个堆内存。

下面我们来看，如何解读第一次 GC 事件，发生在年轻代中的小型GC(Minor GC):

2019-07-14T14:45:37.987+0800 – GC 事件(GC event)开始的时间点.

151.126 – GC 时间的开始时间,相对于JVM的启动时间,单位是秒(Measured in seconds).

GC – 用来区分(distinguish)是 Minor GC 还是 Full GC 的标志(Flag). 这里的 GC 表明本次发生的是 Minor GC.

Allocation Failure – 引起垃圾回收的原因. 本次 GC 是因为年轻代中没有任何合适的区域能够存放需要分配的数据结构而触发的.

DefNew – 使用的垃圾收集器的名字. DefNew 这个名字代表的是: 单线程(single-threaded), 采用标记复制(mark-copy)算法的, 使整个JVM暂停运行(stop-the-world)的年轻代(Young generation) 垃圾收集器(garbage collector).

629119K->69888K – 在本次垃圾收集之前和之后的年轻代内存使用情况(Usage).

(629120K) – 年轻代的总的大小(Total size).

1619346K->1273247K – 在本次垃圾收集之前和之后整个堆内存的使用情况(Total used heap).

(2027264K) – 总的可用的堆内存(Total available heap).

0.0585007 secs – GC事件的持续时间(Duration),单位是秒.

[Times: user=0.06 sys=0.00, real=0.06 secs] – GC事件的持续时间,通过多种分类来进行衡量:

user – 此次垃圾回收, 各个垃圾收集线程消耗的总CPU时间(Total CPU time).

sys – 操作系统调用(OS call) 以及等待系统事件的时间(waiting for system event)

real – 应用程序暂停的时间(Clock time). 由于串行垃圾收集器(Serial Garbage Collector)使用单个线程, 所以 real time 等于 user + sys 的和.

通过上面的分析, 可以计算出在垃圾收集期间，JVM 中的内存使用情况。在垃圾收集之前, 堆内存总的使用了 1.54G (1,619,346K)。其中, 年轻代使用了 614M(629,119k)。也就可以推算出老年代使用的内存为: 967M(990,227K)。


-> 右边的数据中蕴含了更重要的信息, 年轻代的内存使用量，在垃圾回收后下降了 546M(559,231k), 但总的堆内存使用(total heap usage)只减少了 337M(346,099k). 通过这一点可以算出, 有 208M(213,132K) 的年轻代对象被提升到老年代(Old)中。
```



### GC日志2

日志内容：

```html
2019-07-14T14:46:28.829+0800: 200.659: 

  [Full GC (Ergonomics) 

    [PSYoungGen: 64000K->63999K(74240K)] 

    [ParOldGen: 169318K->169318K(169472K)] 233318K->233317K(243712K), 

    [Metaspace: 20427K->20427K(1067008K)], 

  0.1538778 secs] 

  [Times: user=0.42 sys=0.00, real=0.16 secs]
```



日志分析：

```html
分析以上日志内容, 可以得知:

这部分日志截取自JVM启动后200秒左右。

日志片段中显示, 发生了 Full GC。

这次暂停事件的总持续时间是 160毫秒。

在GC完成之后, 几乎所有的老年代空间依然被占用(169318K->169318K(169472K))。

通过日志信息可以确定, 该应用的 GC 情况非常糟糕。而GC的结果是, 老年代空间仍然被占满.

从此示例可以看出, GC日志对监控 GC 行为和 JVM 是否处于健康状态非常有效。


一般情况下, 查看 GC 日志就可以快速确定以下症状:



GC 开销太大。如果 GC 暂停的总时间很长, 就会损害系统的吞吐量。不同的系统允许不同比例的 GC 开销, 但一般认为, 正常范围在 10% 以内。

极个别的 GC 事件暂停时间过长。当某次 GC 暂停时间太长, 就会影响系统的延迟指标. 如果延迟指标规定交易必须在 1,000 ms内完成, 那就不能容忍任何超过 1000毫秒的 GC 暂停。

老年代的使用量超过限制。如果老年代空间在 Full GC 之后仍然接近全满, 那么 GC 就成为了性能瓶颈, 可能是内存太小, 也可能是存在内存泄漏。这种症状会让 GC 的开销暴增。
```

