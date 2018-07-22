## 初识HTTP
更新于2018/07/18


#### session和cookie
http是无协议状态，无法识别客户端的用户信息，为是实现这个目的，它派了2个手下，一个叫cookie，驻扎在客户端；一个叫session驻扎服务端，session它有一个jsessionid是保存在客户端的cookie中，有了这2位得力干将，只要用户从浏览器访问客户端，cookie就会带着jsessionid，如果在服务端匹配成功，就知道这个用户的身份信息了

#### 域名解析
将192.168.1.102这样的ip换个好记的名称,例如：www.baidu.com,这就是域名解析了
#### 域名等级
- 一级域名：.com .cn .org
- 二级域名：baidu.com gov.org
- 三级域名：email.qq.com、 mp.qq.com
    
#### 跨域问题
- 什么是跨域？

**同源策略：** 只有当协议、端口、和域名都相同的页面，则两个页面具有相同的源。浏览器出于安全考虑会进行校验，当发送xhr请求时，三者一个不同就会受到同源策略限制
    
    
请求方法中的OPTIONS，这是个预检查，看看是否支持跨域访问，如果可以，然后再发送一次真正的请求，但是这样太影响性能了（每次都要发送2次，心累）
    
- 跨域解决方法的本质，打个比方

同源：小A给小B写了封信：明天去不去看电影？小B收到信后，回信说：可以啊！这时浏览器爸爸拆开了小B回给小A的信，发现是正常交流，于是把信送给了小A。

跨域：现在小C也写了封信给小B：明天要不要去图书馆，小B也回了封信给小C，浏览器爸爸也拆开了小B回给小C的信，发现不堪入目。拦截掉了。小B不死心，重新回了封信，但这次在response中加上了信息，浏览器爸爸一看，是正常交流，把信送给了小C。
    
    
#### 解决方案
    
- 方案一： 

JSONP：在发送ajax请求时，添加dataType:JSONP，这样就可以了。需要前后端约定，本质是后台返回一个js脚本，然后前端创建出来，再把它销毁

缺点：需要后端改动代码进行配合，如何是调用第三方就不行了；只支持get；发送的不是xhr请求
    
- 方案二：

在后端配置
    
    ```
    /**
	 * spring boot 跨域配置
	 * @return
	 */
	@Bean
	public FilterRegistrationBean registerFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.addUrlPatterns("/**");
		//这里自己要创建一个类CroFilter
		bean.setFilter(new CrosFilter());
		return bean;
	}
	
	
    ```
```
//CrosFilter 类
class CrosFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //允许哪些ip进行访问，* 表示全部，或者一个特定的地址
        response.addHeader("Access-Control-Allow-Origin","http://localhost:8081");
        //response.addHeader("Access-Control-Allow-Origin","*");
        //允许请求的方法,* 表示所有方法，或者也可以单独写，GET,POST,DELETE.....
        response.addHeader("Access-Control-Allow-Methods","GET");
        /**上面这样配置可以解决一般简单的跨域问题，如果是复杂的，那么还要配置contentType*/
        response.addHeader("Access-Control-Allow-Headers","content-type");
        /**上面的配置几乎可以解决所有的跨域了，但是每次请求都是发送一次预检查OPTIONS请求，太影响性能了*/
        //Age的单位是s，这里的意思是告诉浏览器3600s内，不用检查了，只会检查一次就可以了
        response.addHeader("Access-Control-Max-Age","3600");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {}
}
```


协议三要素：

1.语法
2.语义
3.时序

MAC地址是唯一的，每一个硬件都有一个MAC地址是出厂商规定好的