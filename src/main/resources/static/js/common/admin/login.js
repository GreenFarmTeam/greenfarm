jQuery(document).ready(function () {

    App.init();
    //$("#login").click(login);


});
$("#gf_sure").on('click', function () {
    var username = $("#gf_username").val();
    var password = $("#gf_password").val();
    layer.alert("username"+username)
    if ($.trim(username) === "" || $.trim(password) === "") {
        layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        $.ajax({
            type : "POST",
            url : "/greenfarm/admin/password/login/operation",
            dataType : "json",
            data : {
                "username" : username,
                "password" : password
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/index");
                } else {
                    layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                }
            }
        });
    }
});
