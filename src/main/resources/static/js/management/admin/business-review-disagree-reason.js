$("#gf_sure").on('click', function () {
    var reason = $("#gf_reason").val();
    var reviewUid = $("#gf_review_uid").val();
    if ($.trim(reason) === "") {
        layer.alert('请填写未审核通过理由！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (reason.length > 125) {
            layer.alert('理由不允许超过125个字！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            layer.confirm('最终确定驳回申请？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
                $.ajax({
                    type : "POST",
                    url : "/greenfarm/admin/management/business/review/disagree/" + reviewUid + "/operation",
                    dataType : "json",
                    data : {
                        "reason" : reason
                    },
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
        }
    }
});