$("#gf_clear_no_pass").on('click', function () {
    layer.confirm('确定要清楚未通过审核的记录？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "",
            dataType : "json",
            success : function (jsonData) {
                
            }
        });
    }, function () {
        // null operation
    });
});