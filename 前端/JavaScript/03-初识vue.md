# 初识VUE
更新于2018/07/22
### 什么是Vue？
> 一个流行的js框架，vue很大程度上借鉴了angular js，另外加了自己一些比较灵活的特性，轻量级

### Vue中几个重要的基本概念


### Vue的基本的使用

- 指令
> v-on,v-if,v-for,v-bind,v-show...
- 组件
> 组件要先注册后使用，
> 组件是可复用的vue实例

- 核心插件：
    - vue-router https://router.vuejs.org/zh-cn/essentials/getting-started.html

### 组件

#### new一个Vue的实例对象
```
new Vue({
    el: '#app',//vue装载到哪个位置
    //注册局部组件模板，还有全局组件
    template: '<h1>hello {{content}}</h1>',
    //数据绑定
    data: {
        content: 'world!'
    }，
    //注册多个局部
    components: {
        'my-header-a': {
            template: '<h1>这是一个局部组件</h1>'
        }
        ...
    }
})
```
#### 实例一个组件
```
    //子组件构造器
    let child1 = Vue.extend({
        template: '<img src="../img/file_icon.png" width="200">'
    })
```
#### 组件全局注册

```
    // 有template选项，props选项，components选项，data选项，methods选项
    Vue.component('my-component-a',{
        template: '<h1>这是一个全局组件</h1>'
    })
```
#### 父子组件通信
```
    //html
    <div id="app">
         <!-- 使用组件 my-div-->
        <my-div message="我是信息" imgsrc="../img/folder_icon.png"></my-div>
        <my-div message="我也是信息" imgsrc="../img/icon2.png"></my-div>
    </div>
    <!-- 自定义组件 -->
    <template id="my_div">
        <div>
            <p>{{message}}</p>
            <img v-bind:src="imgsrc" width="100"/>
        </div>
    </template>
    
    //js
    //创建全局组件
    Vue.component('my-div',{
        template: '#my_div',
        props:['message','imgsrc']  //父向子传信息用props，里面的message参数会在html中接受参数
    })

```
#### 父组件调用子组件的方法
> 此处有坑应注意，当一个页面调用多次子组件，例如for循环，那么this.$ref是一个数组，需注意

父组件
```
<template>
    <child ref="refchild"></child>
    <el-button @click="clickEVent"></el-button>
</template>
<script>
    compoments : {
        child,
    },
    methods: {
        clickEVent() {
            //调用子组件方法，这里的refchild需要注册，然后直接调用子组件的childMethod
            this.$refs.refchild.childMethod();
            //当然也可以传参
            this.$refs.refchild.childMethod1(“msg”);
        }
    }
</script>
```
子组件
```
<template>
    ...
</template>
<script>
    
    methods: {
        childMethod() {
           alert("调用1");
        }，
        childMethod(msg) {
           alert("调用2"+msg);
        }
    }
</script>
```


#### 计算属性 computed
> 一个属性会有get和set方法，它和methods的区别是计算属性中的值如果没有发生改变，那么下一次调用，就直接从缓存中取，而methods每次取都会重新计算一次，对于大量的计算使用computed属性可以提供性能。计算属性中调用方法不用带"()"

```
    //computed 示例
    // html中调用
    <p>{{reverse}}</p>      //浏览器显示 

    //数据
    data() {
        message: 'Hello World'
    },
    // js 计算属性
    computed: {
        reverse(){
            return this.message.split('').reverse().join('');
        }
    },
```

#### 自定义事件
- 监听$on       
> 绑定要监听的方法，然后再方法中用this.$emit('方法名')去触发
- 触发$emit
> 要触发的那个方法
```
    //html
    <div id="app">
        <!-- 这里绑定了total这个方法-->
        <my-btn @total="allCount()"></my-btn>
        <my-btn @total="allCount()"></my-btn>
        <p>总共点击次数{{totalNum}}次</p>
    </div>
    <!-- 自定义组件 -->
    <template id="my_btn">
        <div>
            <button @click="total()">点击了{{count}}次</button>
        </div>
    </template>
    
    //js
    //创建全局组件
    Vue.component('my-btn',{
        template: '#my_btn',
        data() {
            return {
                count: 0,
            }
        },
        methods: {
            total(){
                this.count += 1;
                this.$emit('total') //触发total方法
            }
        }
    })
    
    let vm = new Vue({
        el: "#app",
        data(){
            return {
                totalNum: 0
            }
        },
        methods: {
            allCount() {
                this.totalNum += 1
            }
        }
    })

```

### slot 插槽
> 作用：可以实现内容的复用

#### 匿名插槽
> 默认替换<slot>标签中的所有内容
```
    // html
    <div id="app">
        <!-- 如果在my-slot标签中没有插入任何内容那么显示原来的内容-->
        <my-slot>匿名插槽默认会替换slot这个标签部分</my-slot>
    </div>
    <!-- 自定义组件 -->
    <template id="my_slot">
        <div>
            <div id="my_header">
                <h3>头部导航区</h3>
            </div>
            <!-- slot 这个部分会被全部替换-->
            <slot>
                <div id="my_content">
                    <h3>内容显示区</h3>
                </div>
            </slot>

            <div id="my_footer">
                <h3>尾部显示区</h3>
            </div>
        </div>
    </template>
    
    //js
    //父组件构造器
    Vue.component('my-slot',{
        template: '#my_slot'
    })
    let vm = new Vue({
        el: "#app"
    })
```

#### 实名插槽
> 通过<slot name="">来替换内容
```
    // html
    <div id="app">
        <my-slot>
            <!-- 这里将slot的名称传入，进行匹配-->
            <div slot="up">覆盖原来的：内容显示区（上）</div>
            <div slot="mid">覆盖原来的：内容显示区（中）</div>
            <div slot="down">覆盖原来的：内容显示区（下）</div>
        </my-slot>
    </div>
    <!-- 自定义组件 -->
    <template id="my_slot">
        <div>
            <h3>头部导航区</h3>
            <!-- 这里指定name的名称-->
            <slot name="up"><h3>内容显示区（上）</h3></slot>
            <slot name="mid"><h3>内容显示区（中）</h3></slot>
            <slot name="down"><h3>内容显示区（下）</h3></slot>
            <h3>尾部显示区</h3>
        </div>
    </template>
    
    //js
    //父组件构造器
    Vue.component('my-slot',{
        template: '#my_slot'
    })
    let vm = new Vue({
        el: "#app"
    })
```
#### vuex
1.按照自己立即的梳理了下vuex

![image](https://raw.githubusercontent.com/liueleven/study/master/%E5%9B%BE%E5%BA%93/01-vue/01-vuex%E7%8A%B6%E6%80%81%E7%AE%A1%E7%90%86%E8%A7%A3%E8%AF%BB.png)


2.vue的生命周期
![image](https://raw.githubusercontent.com/liueleven/study/master/%E5%9B%BE%E5%BA%93/01-vue/02-vue%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F%E5%9B%BE.jpg)







































