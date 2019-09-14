$("#gf_sure").on('click', function () {
    var mail = $("#gf_mail").val();
    var uid = $("#gf_uid").val();
    if ($.trim(mail) === "") {
        layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (mailRegex.test(mail) === false) {
            layer.alert('邮箱格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            layer.confirm('确定要更新绑定邮箱？', {btn:['是','否'], skin:'layui-layer-lan'}, function() {
                layer.closeAll();
                layer.load(0, {shade : 0.5});
                $.ajax({
                    type : "POST",
                    url : "/user/management/mail/data",
                    dataType : "json",
                    data : {
                        "mail" : mail,
                        "uid" : uid
                    },
                    success : function (jsonData) {
                        layer.closeAll('loading');
                        if (jsonData.flag === true) {
                            $(location).attr("href", getPathPrefix() + "user/management/mail/bind/tip");
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