// 刷新图片验证码
function refreshImageVcode() {
    var timestamp = (new Date()).valueOf();
    $("#gf_vcode_img").attr("src", "/graph/vcode?timestamp=" + timestamp);
    $("#gf_vcode").val("");
}

// 图片验证码
$("#gf_vcode_img").on("click", function () {
    var timestamp = (new Date()).valueOf();
    $(this).attr("src", "/graph/vcode?timestamp=" + timestamp);
    $("#gf_vcode").val("");
});

// 登录
$("#gf_login").on('click', function () {
    var key = $("#gf_username").val();
    var password = $("#gf_password").val();
    var vcode = $("#gf_vcode").val();
    if ($.trim(key) === "" || $.trim(password) === "" || $.trim(vcode) === "") {
        layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
        refreshImageVcode();
    } else {
        $.ajax({
            type : "POST",
            url : "/user/password/login/operation",
            dataType : "json",
            data : {
                "key" : key,
                "password" : password,
                "vcode" : vcode
            },
            success : function (jsonData) {
                if (jsonData.flag === false) {
                    layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                    refreshImageVcode();
                } else {
                    $(location).attr("href", getPathPrefix() + "index");
                }
            }
        });
    }
});