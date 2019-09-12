var timer = 70;

// 选择“手机验证方式”注册
$("#gf_register_by_mobile").on("change", function () {
    $("#gf_phone").removeAttr("disabled");
    $("#gf_vcode").removeAttr("disabled");
    $("#gf_get_vcode").removeAttr("disabled");
    $("#gf_mail").val("").attr("disabled", "disabled");
});

// 选择“邮箱验证方式”注册
$("#gf_register_by_mail").on("change", function () {
    $("#gf_phone").val("").attr("disabled", "disabled");
    $("#gf_vcode").val("").attr("disabled", "disabled");
    // TODO 有关这个获取验证码的按钮怎么处理
    $("#gf_get_vcode").attr("disabled", "disabled");
    $("#gf_mail").removeAttr("disabled");
});

// 获取验证码
$("#gf_get_vcode").on('click', function () {
    var flag = $("input[name='gf_register_method']:checked").val();
    var phone = $("#gf_phone").val();
    if (flag !== "1") {
        layer.alert('你所选择的验证方式不是手机验证！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if ($.trim(phone) === "" || phoneRegex.test(phone) === false) {
            layer.alert('手机号格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            layer.load(0, {shade : 0.5});
            $.ajax({
                type : "POST",
                url : "/message/vcode/register",
                dataType : "json",
                data : {
                    "phone" : phone
                },
                success : function (jsonData) {
                    layer.closeAll('loading');
                    if (jsonData.flag === true) {
                        layer.alert('已向' + jsonData.phone + '成功发送验证码，请及时接收！', {skin:'layui-layer-lan', closeBtn: 0});
                        $("#gf_get_vcode").attr("disabled", "disabled");
                        countDown();
                    } else {
                        layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                    }
                }
            });
        }
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
        $("#gf_get_vcode").text("获取短信验证码").removeAttr("disabled");
    }
}

// 注册
$("#gf_register").on("click", function () {
    var flag = $("input[name='gf_register_method']:checked").val();
    var passwordA = $("#gf_password_a").val();
    var passwordB = $("#gf_password_b").val();
    var mail = $("#gf_mail").val();
    var phone = $("#gf_phone").val();
    var vcode = $("#gf_vcode").val();
    if (flag === "2") {
        if ($.trim(passwordA) === "" || $.trim(passwordB) === "" || $.trim(mail) === "") {
            layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            if (passwordRegex.test(passwordA) === false) {
                layer.alert('密码不符合要求！', {skin:'layui-layer-lan', closeBtn: 0});
            } else {
                if (passwordA !== passwordB) {
                    layer.alert('两次密码不一致！', {skin:'layui-layer-lan', closeBtn: 0});
                } else {
                    if (mailRegex.test(mail) === false) {
                        layer.alert('邮箱格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
                    } else {
                        layer.load(0, {shade : 0.5});
                        $.ajax({
                            type : "POST",
                            url : "/user/register/mail/data",
                            dataType : "json",
                            data : {
                                "password" : passwordA,
                                "mail" : mail
                            },
                            success : function (jsonData) {
                                layer.closeAll('loading');
                                if (jsonData.flag === true) {
                                    $(location).attr("href", getPathPrefix() + "user/register/mail/activate/tip");
                                } else {
                                    layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                                }
                            }

                        })
                    }
                }
            }
        }
    }
    if (flag === "1") {
        if ($.trim(passwordA) === "" || $.trim(passwordB) === "" || $.trim(phone) === "" || $.trim(vcode) === "") {
            layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            if (passwordRegex.test(passwordA) === false) {
                layer.alert('密码不符合要求！', {skin:'layui-layer-lan', closeBtn: 0});
            } else {
                if (passwordA !== passwordB) {
                    layer.alert('两次密码不一致！', {skin: 'layui-layer-lan', closeBtn: 0});
                } else {
                    if (phoneRegex.test(phone) === false) {
                        layer.alert('手机号格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
                    } else {
                        layer.load(0, {shade : 0.5});
                        $.ajax({
                            type : "POST",
                            url : "/user/register/phone/operation",
                            dataType : "json",
                            data : {
                                "password" : passwordA,
                                "phone" :  phone,
                                "vcode" : vcode
                            },
                            success : function (jsonData) {
                                layer.closeAll('loading');
                                if (jsonData.flag === true) {
                                    $(location).attr("href", getPathPrefix() + "user/password/login");
                                } else {
                                    layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                                }
                            }
                        });
                    }
                }
            }
        }
    }
});



