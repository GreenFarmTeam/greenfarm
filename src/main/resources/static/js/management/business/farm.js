window.onload = function () {
    var map = initMap("gf_map");
    markPointByClick(map, "gf_lng", "gf_lat");
};

$("#gf_agree").on("change", function () {
    var flag = $(this).is(":checked");
    if (flag === true) {
        $("#gf_sure").removeAttr("disabled");
    } else {
        $("#gf_sure").attr("disabled", "disabled");
    }
});

$("#gf_sure").on('click', function () {
    var formData = new FormData();
    var type = $("#gf_type").val();
    var price = $("#gf_price").val(); // regex
    var year = $("#gf_year").val(); // regex
    var area = $("#gf_area").val(); // regex
    var unit = $("#gf_unit").val();
    var description = $("#gf_desc").val(); // regex
    var mainImage = document.getElementById("gf_main_img").files;
    var otherImages = document.getElementById("gf_other_img").files;
    var lng = $("#gf_lng").val();
    var lat = $("#gf_lat").val();
    if (farmPriceRegex.test(price) === false || farmYearRegex.test(year) === false || farmAreaRegex.test(area) === false || farmDescriptionRegex.test(description) === false) {
        layer.alert('存在信息格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (parseInt(year) > 255 || parseInt(area) > 65535) {
            layer.alert('年限不允许超过255 且 面积不允许超过 65535！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            if (lng === "" || lat === "") {
                layer.alert('请在地图上标注农场的位置！', {skin:'layui-layer-lan', closeBtn: 0});
            } else {
                var flagMain = true;
                var flagOther = true;

                if (mainImage.length !== 0) {
                    var suffixMain = mainImage[0].name.substring(mainImage[0].name.lastIndexOf("."));
                    if (suffixMain !== ".jpg" && suffixMain !== ".jpeg" && suffixMain !== ".gif" && suffixMain !== ".png" && suffixMain !== ".JPG" && suffixMain !== ".JPEG" && suffixMain !== ".GIF" && suffixMain !== ".PNG") {
                        layer.alert('农场主图片文件格式不符！', {skin:'layui-layer-lan', closeBtn: 0});
                        flagMain = false;
                    } else {
                        if (mainImage[0].size >= 2 * 1024 * 1024) {
                            layer.alert('农场主图片大小不能超过2MB！', {skin:'layui-layer-lan', closeBtn: 0});
                            flagMain = false;
                        } else {
                            flagMain = true;
                        }
                    }
                }

                if (otherImages.length !== 0) {
                    if (otherImages.length > 5) {
                        layer.alert('农场其他图片的数量不能超过5张！', {skin:'layui-layer-lan', closeBtn: 0});
                        flagOther = false;
                    } else {
                        var sum = 0;
                        var i;
                        for (i = 0; i < otherImages.length; i++) {
                            sum += otherImages[i].size;
                            if (otherImages[i].size >= 2 * 1024 * 1024) {
                                break;
                            }
                            var suffixOther = otherImages[i].name.substring(otherImages[i].name.lastIndexOf("."));
                            if (suffixOther !== ".jpg" && suffixOther !== ".jpeg" && suffixOther !== ".gif" && suffixOther !== ".png" && suffixOther !== ".JPG" && suffixOther !== ".JPEG" && suffixOther !== ".GIF" && suffixOther !== ".PNG") {
                                break;
                            }
                        }
                        if (i !== otherImages.length) {
                            layer.alert('农场其他图片存在文件格式不符 或 单个图片大小超过2MB！', {skin:'layui-layer-lan', closeBtn: 0});
                            flagOther = false;
                        } else {
                            if (sum >= 10 * 1024 * 1024) {
                                layer.alert('农场其他图片大小不能超过10MB！', {skin:'layui-layer-lan', closeBtn: 0});
                                flagOther = false;
                            } else {
                                flagOther = true;
                            }
                        }
                    }
                }

                if (flagMain === true && flagOther === true) {
                    layer.confirm('确定要发布该农场信息？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        formData.append("type", type);
                        formData.append("price", price);
                        formData.append("year", year);
                        formData.append("area", area);
                        formData.append("unit", unit);
                        formData.append("lng", lng);
                        formData.append("lat", lat);
                        if ($.trim(description) !== "") {
                            formData.append("description", description);
                        }
                        if (mainImage.length !== 0) {
                            formData.append("mainImage", mainImage[0]);
                        }
                        if (otherImages.length !== 0) {
                            for (var i = 0; i < otherImages.length; i++) {
                                formData.append("otherImages", otherImages[i]);
                            }
                        }
                        $.ajax({
                            type : "POST",
                            url : "/business/management/farm/add/operation",
                            dataType : "json",
                            data : formData,
                            contentType : false,
                            processData : false,
                            cache : false,
                            success : function (jsonData) {
                                if (jsonData.flag === true) {
                                    layer.confirm('操作成功，请等待审核！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                                        $(location).attr("href", getPathPrefix() + "business/management/index");
                                    });
                                }
                            }
                        });

                    }, function () {
                        // null operation
                    });
                }
            }
        }
    }

});

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
                    "                农场详情\n" +
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
                var listname2 = ["农场编号：", "农场名称：", "租赁价格：", "价格单位：", "农场描述", "土地类型：", "农场面积(万/平方米)：", "农场状态："];
                var listvalue2 = [jsonData.uid, jsonData.name, jsonData.price, jsonData.farm_unit, jsonData.description, jsonData.farm_type_name, jsonData.farm_area, jsonData.farm_state];
                for(var i = 0; i < listname2.length; i++){
                    content2 += filltext1 + listname2[i] + filltext2 + listvalue2[i] + filltext3;
                }

                if (jsonData.mainImage.indexOf("/") !== -1) {
                    content2 += filltext1 + "农场主图片</label> <div class='controls'><a href='" + getPathPrefix() + jsonData.mainImage + "' target='_blank'>" +
                        "<img src='" + getPathPrefix() + jsonData.mainImage + "' width='220px' height='180px'/></a></div></div>";
                } else {
                    content2 += filltext1 + "农场主图片</label> <div class='controls'><span>"+jsonData.mainImage+"</span></div></div>";
                }

                if (jsonData.otherImages.length !== 0) {
                    content2 += filltext1 + "农场其他图片</label> <div class='controls'>";
                    for (var i in jsonData.otherImages) {
                        content2 += "<a href='" + getPathPrefix() + jsonData.otherImages[i] + "' target='_blank'><img src='" +
                            getPathPrefix() + jsonData.otherImages[i] + "' width='220px' height='180px' style='margin-bottom: 10px'/></a>";
                    }
                    content2 += "</div></div>";
                } else {
                    content2 += filltext1 + "农场其他图片</label> <div class='controls'><span>暂无其他农场图片</span></div></div>";
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
    })

});

$("button[name='gf_up']").on('click', function () {
    var farmUid = $(this).attr("value");
    layer.confirm('确定恢复上架该农场？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/greenfarm/business/management/farm/up/operation",
            dataType : "json",
            data : {
                "uid" : farmUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "business/management/farm/down");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});

$("a[name='gf_location']").on('click', function () {
    var lng = $(this).attr("data-lng");
    var lat = $(this).attr("data-lat");
    var content = "<!DOCTYPE html>" +
        "<html>" +
        "<head>" +
        "<meta charset='UTF-8'>" +
        "</head>" +
        "<body>" +
        "<div id='container' style='width: 600px; height: 450px'></div>" +
        "<script type='text/javascript'>" +
        "var map = new AMap.Map('container', {" +
        "zoom : 14," +
        "resizeEnable : false});" +
        "markPoint(map, " + lng + ", " + lat + ");" +
        "</script>" +
        "</body>" +
        "</html>";
    layer.open({
        type: 1,
        skin: 'layui-layer-rim',
        area: ['600px', '450px'],
        shadeClose: false,
        content: content
    });
});

$("button[name='gf_down']").on('click', function () {
    var farmUid = $(this).attr("value");
    layer.confirm('确定下架该农场（下架前的农场合同仍然有效）？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
        $.ajax({
            type : "GET",
            url : "/greenfarm/business/management/farm/down/operation",
            dataType : "json",
            data : {
                "uid" : farmUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.closeAll();
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "/business/management/farm/up");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});

function upLoad(){
    var fileInput = document.getElementById("gf_main_img");
    var file = fileInput.files[0];
    //创建读取文件的对象
    var reader = new FileReader();
    //创建文件读取相关的变量
    var imgFile;
    //为文件读取成功设置事件
    reader.οnlοad=function(e) {
        // alert('文件读取完成');
        imgFile = e.target.result;
        console.log(imgFile);
        $("#main_img_contain").css("display","block");
        $("#main_img").attr('src',imgFile);
    };

    //正式读取文件
    reader.readAsDataURL(file);
}