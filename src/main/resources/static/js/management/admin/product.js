
$("button[name='gf_up']").on('click', function () {
    var productUid = $(this).attr("value");
    layer.confirm('确定恢复上架该商品？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/greenfarm/admin/management/product/up/operation",
            dataType : "json",
            data : {
                "uid" : productUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/product/down");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});

$("button[name='gf_down']").on('click', function () {
    var productUid = $(this).attr("value");
    layer.confirm('确定下架该商品（下架前的订单仍然有效）？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
        $.ajax({
            type : "GET",
            url : "/greenfarm/admin/management/product/down/operation",
            dataType : "json",
            data : {
                "uid" : productUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.closeAll();
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/product/up");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});

$("button[name='gf_agree']").on('click', function () {
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

$("button[name='gf_disagree']").on('click', function () {
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

$("#gf_reason_sure").on('click', function () {
    var reason = $("#gf_reason").val();
    var reviewUid = $("#gf_review_uid").val();
    if ($.trim(reason) === "") {
        layer.alert('请填写未审核通过理由！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (reason.length > 125) {
            layer.alert('理由不允许超过125个字！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            layer.closeAll();
            layer.confirm('最终确定驳回申请？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
                layer.closeAll();
                $.ajax({
                    type : "POST",
                    url : "/greenfarm/admin/management/product/review/disagree/" + reviewUid + "/operation",
                    dataType : "json",
                    data : {
                        "reason" : reason
                    },
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
        }
    }
});

$("button[name='gf_detail']").on('click', function () {
    var productUid = $(this).attr("value");
    $.ajax({
        type : "GET",
        url : "/greenfarm/admin/management/product/detail/operation",
        dataType : "json",
        data : {
            "uid" : productUid
        },
        success : function (jsonData) {
            if (jsonData.flag === true) {
                var top = "<!DOCTYPE html>\n" +
                    "<html lang='en'>\n" +
                    "<script type='text/javascript' src='/gfstatic/js/common/url.js'></script>\n" +
                    "<head>\n" +
                    "    <meta charset='UTF-8'>\n" +
                    "    <title>Title</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div class='row-fluid'>\n" +
                    "    <div class='portlet box blue'>\n" +
                    "        <div class='portlet-title'>\n" +
                    "            <div class='caption'>\n" +
                    "                <i class='fa fa-reorder'></i>\n" +
                    "                商品详情\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        <div class='portlet-body'>\n" +
                    "            <form class='form-horizontal'>\n" +
                    "                <div style='width: 40%; margin: 0 4%; float:left;'>";

                var filltext1 = "<div class='control-group'>\n" +
                    "                        <label class='control-label'>";
                var filltext2 = "</label>\n" +
                    "                        <div class='controls'>\n" +
                    "                            <input name='id' value='";
                var filltext3 = "' readonly='readonly' type='text' class='m-wrap media'/>\n" +
                    "                        </div>\n" +
                    "                    </div>";

                var content1 = "";
                var listname1 = ["店铺编号：", "店铺名称：", "店铺描述：", "商家会员号：", "商家昵称："];
                var listvalue1 = [jsonData.businessUid, jsonData.businessName, jsonData.businessDescription, jsonData.username, jsonData.nickname];
                for(var i = 0; i < listname1.length; i++){
                    content1 += filltext1 + listname1[i] + filltext2 + listvalue1[i] + filltext3;
                }

                var midcontent = "</div>\n" +
                    "\n" +
                    "                <div style='width: 40%; margin: 0 4%; float:left;'>";

                var content2 = "";
                var listname2 = ["商品编号：", "商品名称：", "商品单价：", "商品类型：", "商品描述：", "当前库存：", "成功上架日期：", "提交审核日期"];
                var listvalue2 = [jsonData.uid, jsonData.name, jsonData.price, jsonData.type, jsonData.description, jsonData.stock, jsonData.upDate, jsonData.submitDate];
                for(var i = 0; i < listname2.length; i++){
                    content2 += filltext1 + listname2[i] + filltext2 + listvalue2[i] + filltext3;
                }

                if (jsonData.mainImage.indexOf("/") !== -1) {
                    content2 += filltext1 + "商品主图片</label> <div class='controls'><a href='" + getPathPrefix() + jsonData.mainImage + "' target='_blank'>" +
                        "<img src='" + getPathPrefix() + jsonData.mainImage + "' width='220px' height='180px'/></a></div></div>";
                } else {
                    content2 += filltext1 + "商品主图片</label> <div class='controls'><span>"+jsonData.mainImage+"</span></div></div>";
                }

                if (jsonData.otherImages.length !== 0) {
                    content2 += filltext1 + "商品其他图片</label> <div class='controls'>";
                    for (var i in jsonData.otherImages) {
                        content2 += "<a href='" + getPathPrefix() + jsonData.otherImages[i] + "' target='_blank'><img src='" +
                            getPathPrefix() + jsonData.otherImages[i] + "' width='220px' height='180px' style='margin-bottom: 10px'/></a>";
                    }
                    content2 += "</div></div>";
                } else {
                    content2 += filltext1 + "商品其他图片</label> <div class='controls'><span>暂无其他商品图片</span></div></div>";
                }

                var foot = "</div>\n" +
                    "\n" +
                    "                <div style='clear:both'></div>\n" +
                    "            </form>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";
                layer.closeAll();
                layer.open({
                    type: 1,
                    skin: 'layui-layer-rim',
                    area: ['1000px', '600px'],
                    shadeClose: true,
                    content : top + content1 + midcontent + content2 + foot
                });
            }
        }
    });
});