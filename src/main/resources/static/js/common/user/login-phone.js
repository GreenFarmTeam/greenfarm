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

            }
        });
    }
});