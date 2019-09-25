$("#gf_clear_no_pass").on('click', function () {
    layer.confirm('确定要清楚未通过审核的记录？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/business/management/product/remove/nopass/operation",
            dataType : "json",
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "business/management/product/review");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});