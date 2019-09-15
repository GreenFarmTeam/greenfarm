$("#gf_mail").on('click', function () {
    layer.confirm('确定要通过邮箱找回会员号？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
        layer.closeAll();
        layer.load(0, {shade : 0.5});
        $.ajax({
            type : "GET",
            url : "/user/forget/username/method/mail",
            dataType : "json",
            success : function (jsonData) {
                layer.closeAll('loading');
                if (jsonData.flag === true) {
                    layer.confirm('已经向' + jsonData.mail + '成功发送邮件，请查收！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function() {
                        $(location).attr("href", getPathPrefix() + "user/password/login");
                    });
                } else {
                    layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                }
            }
        });
    }, function () {
        // null operation
    });
});

$("#gf_phone").on('click', function () {
    layer.confirm('确定要通过手机短信找回会员号？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
        layer.closeAll();
        layer.load(0, {shade : 0.5});
        $.ajax({
            type : "GET",
            url : "/user/forget/username/method/phone",
            dataType : "json",
            success : function (jsonData) {
                layer.closeAll('loading');
                if (jsonData.flag === true) {
                    layer.confirm('已经向' + jsonData.phone + '成功发送短信，请查收！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function() {
                        $(location).attr("href", getPathPrefix() + "user/password/login");
                    });
                } else {
                    layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                }
            }
        });
    }, function () {
        // null operation
    });
});