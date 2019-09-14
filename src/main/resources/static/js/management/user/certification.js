$("#gf_sure").on('click', function () {
    var realname = $("#gf_realname").val();
    var idcard = $("#gf_idcard").val();
    var uid = $("#gf_uid").val();
    if ($.trim(realname) === "" || $.trim(idcard) === "") {
        layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (idcardRegex.test(idcard) === false) {
            layer.alert('身份证格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            layer.confirm('确定要更新实名认证？', {btn:['是','否'], skin:'layui-layer-lan'}, function() {
                layer.closeAll();
                layer.load(0, {shade : 0.5});
                $.ajax({
                    type : "POST",
                    url : "/user/management/certification/operation/" + uid,
                    dataType : "json",
                    data : {
                        "realname" : realname,
                        "idcard" : idcard
                    },
                    success : function (jsonData) {
                        layer.closeAll('loading');
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