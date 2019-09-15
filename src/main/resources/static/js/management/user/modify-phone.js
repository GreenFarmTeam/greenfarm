var timer = 70;

// 获取验证码
$("#gf_get_vcode").on('click', function () {
    var phone = $("#gf_phone").val();
    if ($.trim(phone) === "" || phoneRegex.test(phone) === false) {
        layer.alert('手机号格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        layer.load(0, {shade : 0.5});
        $.ajax({
            type : "POST",
            url : "/message/vcode/bind",
            dataType : "json",
            data : {
                "phone" : phone
            },
            success : function (jsonData) {
                layer.closeAll('loading');
                if (jsonData.flag === true) {
                    layer.alert('已向' + jsonData.phone + '成功发送验证码，请及时接收，5分钟内有效！', {skin:'layui-layer-lan', closeBtn: 0});
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
    $("#gf_get_vcode").val(timer + "s后可以重新获取验证码");
    if (timer >= 1) {
        setTimeout(countDown, 1000);
    } else {
        timer = 70;
        $("#gf_get_vcode").val("获取短信验证码").removeAttr("disabled");
    }
}

// 绑定
$("#gf_sure").on('click', function () {
    // TODO
    var phone = $("#gf_phone").val();
    var vcode = $("#gf_vcode").val();
    var uid = $("#gf_uid").val();
    if ($.trim(phone) === "" || $.trim(vcode) === "") {
        layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (phoneRegex.test(phone) === false) {
            layer.alert('手机号格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            layer.confirm('确定要更新绑定手机？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
                $.ajax({
                    type : "POST",
                    url : "/user/management/phone/operation/" + uid,
                    dataType : "json",
                    data : {
                        "phone" : phone,
                        "vcode" : vcode
                    },
                    success : function (jsonData) {
                        if (jsonData.flag === true) {
                            $(location).attr("href", getPathPrefix() + "user/management/index");
                        } else {
                            layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                        }
                    }
                });
            }, function () {
                // null operation
            });
        }
    }
});