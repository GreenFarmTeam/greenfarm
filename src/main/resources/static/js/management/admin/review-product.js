$("a[name='gf_agree']").on('click', function () {
    var reviewUid = $(this).attr("value");
    layer.confirm('确定同意上架该商品？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
        $.ajax({
            type : "GET",
            url : "/greenfarm/admin/management/product/review/agree/" + reviewUid + "/operation",
            dataType : "json",
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function() {
                        $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/product/review");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});

$("a[name='gf_disagree']").on('click', function () {
    var reviewUid = $(this).attr("value");
    layer.confirm('确定驳回该商品上架申请？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        layer.closeAll();
        layer.confirm('需要告知未审核通过理由！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function() {
            layer.closeAll();
            layer.open({
                type : 1,
                skin : 'layui-layer-rim',
                area : ['420px', '240px'],
                shadeClose : true,
                content : "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<script type='text/javascript' src='/gfstatic/webjars/jquery/3.4.1/jquery.min.js'></script>" +
                "<script type='text/javascript' src='/gfstatic/templateone/res/layui/layui.all.js'></script>" +
                "<script type='text/javascript' src='/gfstatic/js/common/url.js'></script>" +
                "<script type='text/javascript' src='/gfstatic/js/common/regex.js'></script>" +
                "</head>" +
                "<body>" +
                "<form>" +
                "<h4>理由</h4>" +
                "<textarea id='gf_reason'></textarea>" +
                "<br/>" +
                "<br/>" +
                "<input type='button' value='确定' id='gf_reason_sure'/>" +
                "</form>" +
                "<input type='hidden' value='" + reviewUid + "' id='gf_review_uid'/>" +
                "<script type='text/javascript' src='/gfstatic/js/management/admin/review-product-disagree-reason.js'></script>" +
                "</body>" +
                "</html>"
            });
        });
    }, function () {
        // null operation
    });
});