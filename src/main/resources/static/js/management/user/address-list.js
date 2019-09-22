// 删除
$("a[name=gf_delete]").on('click', function () {
    var uid = $(this).attr("value");
    layer.confirm('确定要删除收货地址？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/user/management/address/remove/operation",
            dataType : "json",
            data : {
                "uid" : uid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "user/management/address");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});