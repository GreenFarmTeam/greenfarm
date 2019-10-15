$("button[name='gf_detail']").on('click',function () {
    var farmUid = $(this).attr("value");
    $.ajax({
        type:"GET",
        url:"/greenfarm/admin/management/farm/detail/operation",
        dataType:"json",
        data:{
            "uid":farmUid
        },
        success : function (jsonData) {

            if(jsonData.flag == true){

                var content = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "<script type='text/javascript' src='/gfstatic/js/common/url.js'></script>" +
                    "</head>" +
                    "<body>" +
                    "<table border='1'>" +
                    "<tr>" +
                    "<td>农场编号</td>" +
                    "<td>" + jsonData.uid + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>农场名称</td>" +
                    "<td>" + jsonData.name + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>租赁价格</td>" +
                    "<td>" + jsonData.price + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>价格单位</td>" +
                    "<td>" + jsonData.farm_unit + "</td>" +
                    "</tr>" +

                    "<tr>" +
                    "<td>农场描述</td>" +
                    "<td>" + jsonData.description + "</td>" +
                    "</tr>" +

                    "<tr>"+
                    "<td>土地类型</td>" +
                    "<td>" + jsonData.farm_type_name + "</td>" +
                    "</tr>"+

                    "<tr>"+
                    "<td>农场面积(万/平方米)</td>" +
                    "<td>" + jsonData.farm_area + "</td>" +
                    "</tr>"+

                    "<tr>" +
                    "<td>农场状态</td>" +
                    "<td>" + jsonData.farm_state + "</td>" +
                    "</tr>" +

                    "<tr>";


                var suffix = "</table>" +
                    "<hr/>" +
                    "<table border='1'>" +

                    "<tr>" +
                    "<td>店铺编号</td>" +
                    "<td>" + jsonData.businessUid + "</td>" +
                    "</tr>" +

                    "<tr>" +
                    "<td>店铺名称</td>" +
                    "<td>" + jsonData.businessName + "</td>" +
                    "</tr>" +

                    "<tr>" +
                    "<td>店铺描述</td>" +
                    "<td>" + jsonData.businessDescription + "</td>" +
                    "</tr>" +

                    "<tr>" +
                    "<td>商家会员号</td>" +
                    "<td>" + jsonData.username + "</td>" +
                    "</tr>" +

                    "<tr>" +
                    "<td>商家昵称</td>" +
                    "<td>" + jsonData.nickname + "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</body>" +
                    "</html>";

                if (jsonData.mainImage.indexOf("/") !== -1) {
                    content = content + "<tr>" +
                        "<td>农场主图片</td>" +
                        "<td>" +
                        "<a href='" + getPathPrefix() + jsonData.mainImage + "' target='_blank'>" +
                        "<img src='" + getPathPrefix() + jsonData.mainImage + "' width='100px' height='100px'/>" +
                        "</a>" +
                        "</td>" +
                        "</tr>";
                } else {
                    content = content + "<tr>" +
                        "<td>农场主图片</td>" +
                        "<td>" + jsonData.mainImage + "</td>" +
                        "</tr>"
                }
                if (jsonData.otherImages.length !== 0) {
                    content = content + "<tr>" +
                        "<td>农场其他图片</td>" +
                        "<td>";
                    for (var i in jsonData.otherImages) {
                        content = content + "<a href='" + getPathPrefix() + jsonData.otherImages[i] + "' target='_blank'>" +
                            "<img src='" + getPathPrefix() + jsonData.otherImages[i] + "' width='100px' height='100px'/>" +
                            "</a>";
                    }
                    content = content + "</td>" +
                        "</tr>"
                } else {
                    content = content + "<tr>" +
                        "<td>农场其他图片</td>" +
                        "<td>暂无其他农场图片</td>" +
                        "</tr>";
                }
                layer.closeAll();
                layer.open({
                    type: 1,
                    skin: 'layui-layer-rim',
                    area: ['800px', '600px'],
                    shadeClose: true,
                    content : content + suffix
                });
            }

        }
    })

})

$("button[name='gf_up']").on('click', function () {
    var farmUid = $(this).attr("value");
    layer.confirm('确定恢复上架该农场？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/greenfarm/admin/management/farm/up/operation",
            dataType : "json",
            data : {
                "uid" : farmUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/farm/down");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});

$("button[name='gf_down']").on('click', function () {
    var farmUid = $(this).attr("value");
    layer.confirm('确定下架该农场（下架前的农场合同仍然有效）？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
        $.ajax({
            type : "GET",
            url : "/greenfarm/admin/management/farm/down/operation",
            dataType : "json",
            data : {
                "uid" : farmUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.closeAll();
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/farm");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});

$("a[name='gf_agree']").on('click', function () {
    var reviewUid = $(this).attr("value");
    layer.confirm('确定同意上架该农场？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
        $.ajax({
            type : "GET",
            url : "/greenfarm/admin/management/farm/review/agree/" + reviewUid + "/operation",
            dataType : "json",
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function() {
                        $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/farm/review");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});

$("button[name='disagree']").on('click', function () {
    var reviewUid = $(this).attr("value");
    layer.confirm('确定驳回该农场的上架申请？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
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
                "<script type='text/javascript' src='/gfstatic/js/management/admin/review-farm-disagree-reason.js'></script>" +
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
                    url : "/greenfarm/admin/management/farm/review/disagree/" + reviewUid + "/operation",
                    dataType : "json",
                    data : {
                        "reason" : reason
                    },
                    success : function (jsonData) {
                        if (jsonData.flag === true) {
                            layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function() {
                                $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/farm/review");
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