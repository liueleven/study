var baseUrl = 'http://www.liuzuoqi.com'; // 后台api TODO 需要替换
// var baseUrl = 'http://meg-test.sunnyxiao.com'; // 后台api
var sharedUrl;
var shareTitle = 'MGE电竞';
var shareDesc = 'MGE——Make Great Efforts 立志为电竞玩家做出最大的努力，做玩家最贴心的电竞服务圈';
function getSignUrl() {
    // debugger
    // alert("getSignUrl")
    sharedUrl = location.href.split('#')[0];  //获取当前页面的url
    sharedUrl = encodeURIComponent(sharedUrl);
    return baseUrl + '/verity/wechat/share?url=' + sharedUrl;
}
var config = {
    title: shareTitle, // 分享标题
    // title: 'MGE电竞', // 分享标题
    desc: shareDesc,
    imgUrl: 'http://wx4.sinaimg.cn/mw690/0060lm7Tly1ftv42xif9uj30hs0hs3z3.jpg', // 分享图标
    link: sharedUrl, // 分享链接，该链接域名必须与当前企业的可信域名一致
    type: '',
    dataUrl: '',
    success: function () {
        // 用户确认分享后执行的回调函数
    },
    cancel: function () {
        // 用户取消分享后执行的回调函数
    }
};
function doConfig(result) {
    // debugger
    // alert("doConfig")
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: result.appId, // 必填，企业号的唯一标识，此处填写企业号corpid
        timestamp: result.timestamp, // 必填，生成签名的时间戳
        nonceStr: result.nonceStr, // 必填，生成签名的随机串
        signature: result.signature,// 必填，签名，见附录1
        jsApiList: [
            'onMenuShareAppMessage',
            'onMenuShareTimeline'
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });

    wx.ready(function () {
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
        // 分享到朋友圈
        wx.onMenuShareTimeline(config);
        //分享给朋友
        wx.onMenuShareAppMessage(config);
    });
    //
    // wx.checkJsApi({
    //     jsApiList: ['onMenuShareAppMessage','onMenuShareTimeline'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
    //     success: function(res) {
    //         // 以键值对的形式返回，可用的api值true，不可用为false
    //         // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
    //     }
    // });
}


$.get(getSignUrl(), function (result, status) {
    if (status === 'success') {
        doConfig(result);
    } else {
        alert('无法获取token信息');
    }
});

