$("button[name='gf_agree']").on('click', function () {
    var reviewUid = $(this).attr("value");
    layer.confirm('确定同意该申请？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
        $.ajax({
            type : "GET",
            url : "/greenfarm/admin/management/business/review/agree/" + reviewUid,
            dataType : "json",
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function() {
                        $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/business/review");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});


$("button[name='gf_disagree']").on('click', function () {
    var reviewUid = $(this).attr("value");
    layer.confirm('确定驳回该申请？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
        layer.confirm('需要告知未审核通过理由！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function() {
            $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/business/review/disagree/" + reviewUid);
        });
    }, function () {
        // null operation
    });
});


$("button[name='gf_detail']").on('click', function () {
    var reviewUid = $(this).attr("value");
    $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/business/review/detail/" + reviewUid);
});