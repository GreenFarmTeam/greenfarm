var timer = 70;

// 获取动态密码
$("#gf_get_vcode").on('click', function () {
    var phone = $("#gf_phone").val();
    if ($.trim(phone) === "" || phoneRegex.test(phone) === false) {
        layer.alert('手机号格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        layer.load(0, {shade : 0.5});
        $.ajax({
            type : "POST",
            url : "/message/vcode/login",
            dataType : "json",
            data : {
                "phone" : phone
            },
            success : function (jsonData) {
                layer.closeAll('loading');
                if (jsonData.flag === true) {
                    layer.alert('已向' + jsonData.phone + '成功发送验证码，请及时接收，3分钟内有效！', {skin:'layui-layer-lan', closeBtn: 0});
                    $("#gf_get_vcode").attr("disabled", "disabled");
                    countDown();
                } else {
                    layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                }
            }
        });
    }
});

// 倒计时
function countDown() {
    timer--;
    $("#gf_get_vcode").val(timer + "s后可以重新获取动态密码");
    if (timer >= 1) {
        setTimeout(countDown, 1000);
    } else {
        timer = 70;
        $("#gf_get_vcode").val("获取动态密码").removeAttr("disabled");
    }
}

// 登录
$("#gf_login").on('click', function () {
    var phone = $("#gf_phone").val();
    var vcode = $("#gf_vcode").val();
    if ($.trim(phone) === "" || $.trim(vcode) === "") {
        layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (phoneRegex.test(phone) === false) {
            layer.alert('手机号格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            $.ajax({
                type : "POST",
                url : "/user/mobile/login/operation",
                dataType : "json",
                data : {
                    "phone" : phone,
                    "vcode" : vcode
                },
                success : function (jsonData) {
                    if (jsonData.flag === false) {
                        layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                    } else {
                        $(location).attr("href", getPathPrefix() + "index");
                    }
                }
            });
        }
    }
});