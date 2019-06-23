# jQuery常用技巧

> jQuery使用中的一些小技巧，方便以后查阅

### 获取checkbox中的值
 $('input[name="checkbox"]:checked')
 > 这个表示获取所有input标签，name=checkbox，并且是checked选中状态的
 ```
 //遍历所有选中的CheckBox他们的value值
 $('input[name="checkbox"]:checked').each(function(){
    alert($(this).val());
});
 ```
 
 ### ajax-传递数组到后端
```
$.ajax({
    url: '',
    type: 'POST',
    data: {gamesId:gamesId},
    traditional: true,  //传递数组加上这个
    success: function (res) {
        if(res.success) {
            console.log("success!")
        }
    }
})
```

#### 获取和选中下拉选select的值
```
//html
<select id="idType">
    <!--disabled selected hidden 加上这个选项就不会出现证件类型了 -->
    <option value="" disabled selected hidden>证件类型</option>
    <option value="mainland">身份证</option>
    <option value="hkmacao">港澳通行证</option>
    <option value="passport">护照</option>
</select>

//js
//获取：
var selectedObj = $("#idType option:selected");
var idType = selectedObj.val();

//选中：
$("#idType").val('mainland');
```

 