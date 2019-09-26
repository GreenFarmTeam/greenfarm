$("a[name='gf_up']").on('click', function () {
    var productUid = $(this).attr("value");
    layer.confirm('确定恢复上架该商品？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/business/management/product/up/operation",
            dataType : "json",
            data : {
                "uid" : productUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.closeAll();
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "business/management/product/down");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});


$("a[name='gf_detail']").on('click', function () {
    var productUid = $(this).attr("value");
    $.ajax({
        type : "GET",
        url : "/business/management/product/detail",
        dataType : "json",
        data : {
            "uid" : productUid
        },
        success : function (jsonData) {
            if (jsonData.flag === true) {
                var content = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "<script type='text/javascript' src='/gfstatic/js/common/url.js'></script>" +
                    "</head>" +
                    "<body>" +
                    "<table border='1'>" +
                    "<tr>" +
                    "<td>商品名称</td>" +
                    "<td>" + jsonData.name + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>商品单价</td>" +
                    "<td>" + jsonData.price + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>商品库存</td>" +
                    "<td>" + jsonData.stock + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>商品类型</td>" +
                    "<td>" + jsonData.type + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>商品描述</td>" +
                    "<td>" + jsonData.description + "</td>" +
                    "</tr>";
                var suffix = "</table>" +
                    "</body>" +
                    "</html>";
                if (jsonData.mainImage.indexOf("/") !== -1) {
                    content = content + "<tr>" +
                        "<td>商品主图片</td>" +
                        "<td>" +
                        "<a href='" + getPathPrefix() + jsonData.mainImage + "' target='_blank'>" +
                        "<img src='" + getPathPrefix() + jsonData.mainImage + "' width='100px' height='100px'/>" +
                        "</a>" +
                        "</td>" +
                        "</tr>";
                } else {
                    content = content + "<tr>" +
                        "<td>商品主图片</td>" +
                        "<td>" + jsonData.mainImage + "</td>" +
                        "</tr>"
                }
                if (jsonData.otherImages.length !== 0) {
                    console.log(jsonData.otherImages);
                    content = content + "<tr>" +
                        "<td>商品其他图片</td>" +
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
                        "<td>商品其他图片</td>" +
                        "<td>暂无其他商品图片</td>" +
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
    });
});