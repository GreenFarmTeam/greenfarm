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
    var key = $("#gf_key").val();
    var vcode = $("#gf_vcode").val();
    if ($.trim(key) === "" || $.trim(vcode) === "") {
        refreshImageVcode();
    } else {
        $.ajax({
            type : "POST",
            url : "/user/forget/password/operation",
            dataType : "json",
            data : {
                "key" : key,
                "vcode" : vcode
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    $(location).attr("href", getPathPrefix() + "user/forget/password/method");
                } else {
                    layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                    refreshImageVcode();
                }
            }
        });
    }
});