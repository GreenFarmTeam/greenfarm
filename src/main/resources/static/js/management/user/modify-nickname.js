$("#gf_modify").on('click', function () {
    var uid = $("#gf_uid").val();
    var newNickname = $("#gf_nickname").val();
    var oldNickname = $("#gf_old_nickname").val();
    if (newNickname === oldNickname) {
        layer.alert('昵称并未修改！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (nicknameRegex.test(newNickname) === false) {
            layer.alert('昵称格式不符合要求！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            layer.confirm('确定要修改？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
                $.ajax({
                    type : "POST",
                    url : "/user/management/nickname/operation/" + uid,
                    dataType : "json",
                    data : {
                        "nickname" : newNickname
                    },
                    success : function (jsonData) {
                        if (jsonData.flag === true) {
                            $(location).attr("href", getPathPrefix() + "user/management/index");
                        }
                    }
                });
            }, function() {
                // null operation
            });
        }
    }
});