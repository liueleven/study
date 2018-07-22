# 饿了么Element-UI
> 好用，完美兼容vue，感谢 : ) [官方链接](http://element-cn.eleme.io/#/zh-CN/component/)
### 使用笔记
如何在table中格式化字段
>  在表格中如果像格式化某列的值，可以使用 :formatter="methodName"，默认会传入2个值，row和column，可以在初始化表格的时候就加载这个方法完成指定的
	功能。例如时间戳转化等等
```
<el-table-column
    :formatter="dateFormat"
    align="center"
    prop="createTime"
    width="180"
    label="创建日期">
</el-table-column>
```
	
table中不能自适应屏幕宽度
> 表格table如果设置了width="100%"还是不能自适应，那么试着去掉列中的width属性，可能是它限制了，或者上下换下位置	