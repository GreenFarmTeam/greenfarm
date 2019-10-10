$("a[name='gf_detail']").on('click',function () {
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