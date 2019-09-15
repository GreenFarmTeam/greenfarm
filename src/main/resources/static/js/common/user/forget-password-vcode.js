$("#gf_sure").on('click', function () {
    var vcode = $("#gf_vcode").val();
    if ($.trim(vcode) === "") {
        layer.alert('请填写完整信息', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        $.ajax({
            type : "POST",
            url : "/user/forget/password/method/check/operation",
            dataType : "json",
            data : {
                "vcode" : vcode
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    $(location).attr("href", getPathPrefix() + "user/forget/password/modify/password");
                } else {
                    layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                }
            }
        });
    }
});