// 图片验证码
$("#gf_vcode_img").on("click", function () {
    var timestamp = (new Date()).valueOf();
    $(this).attr("src", "/graph/vcode?timestamp=" + timestamp);
    $("#gf_vcode").val("");
});

// 刷新图片验证码
function refreshImageVcode() {
    var timestamp = (new Date()).valueOf();
    $("#gf_vcode_img").attr("src", "/graph/vcode?timestamp=" + timestamp);
    $("#gf_vcode").val("");
}


// 确认
$("#gf_sure").on('click', function () {
    var idcard = $("#gf_idcard").val();
    var realname = $("#gf_realname").val();
    var vcode = $("#gf_vcode").val();
    if ($.trim(idcard) === "" || $.trim(realname) === "" || $.trim(vcode) === "") {
        layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
        refreshImageVcode();
    } else {
        if (idcardRegex.test(idcard) === false) {
            layer.alert('身份证格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
            refreshImageVcode();
        } else {
            $.ajax({
                type : "POST",
                url : "/user/forget/username/operation",
                dataType : "json",
                data : {
                    "idcard" : idcard,
                    "realname" : realname,
                    "vcode" : vcode
                },
                success : function (jsonData) {
                    if (jsonData.flag === false) {
                        layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                        refreshImageVcode();
                    } else {
                        $(location).attr("href", getPathPrefix() + "user/forget/username/method");
                    }
                }
            });
        }
    }
});