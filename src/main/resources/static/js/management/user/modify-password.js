// 确认修改密码
$("#gf_sure").on('click', function () {
    var passwordA = $("#gf_password_a").val();
    var passwordB = $("#gf_password_b").val();
    var uid = $("#gf_uid").val();
    if ($.trim(passwordA) === "" || $.trim(passwordB) === "") {
        layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (passwordRegex.test(passwordA) === false || passwordRegex.test(passwordB) === false) {
            layer.alert('密码格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            if (passwordA !== passwordB) {
                layer.alert('两次密码不一致！', {skin:'layui-layer-lan', closeBtn: 0});
            } else {
                layer.confirm('确定要修改密码？', {btn:['是','否'], skin:'layui-layer-lan'}, function() {
                    $.ajax({
                        type : "POST",
                        url : "/user/management/password/operation/" + uid,
                        dataType : "json",
                        data : {
                            "password" : passwordA
                        },
                        success : function (jsonData) {
                            if (jsonData.flag === true) {
                                $(location).attr("href", getPathPrefix() + "user/management/index");
                            }
                        }
                    });
                }, function () {
                    // null operation
                });

            }
        }
    }
});