// 注销
$("#gf_logout").on('click', function () {
    layer.confirm('确定要退出？', {btn:['是','否'], closeBtn:0}, function() {
        $.ajax({
            type : "GET",
            url : "/greenfarm/admin/logout/operation",
            dataType : "json",
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    $(location).attr("href", getPathPrefix() + "greenfarm/admin/password/login");
                }
            }
        });
    }, function() {
        // null operation
    });
});