$("button[name='gf_up']").on('click', function () {
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

$("#gf_agree").on('change', function () {
    var flag = $(this).is(":checked");
    if (flag === true) {
        $("#gf_sure").removeAttr("disabled");
    } else {
        $("#gf_sure").attr("disabled", "disabled");
    }
});

$("#gf_sure").on('click', function () {
    var formData = new FormData();
    var name = $("#gf_name").val();
    var price = $("#gf_price").val();
    var unit = $("#gf_unit").val();
    var description = $("#gf_desc").val();
    var type = $("#gf_type").val();
    var mainImage = document.getElementById("gf_main_img").files;
    var otherImages = document.getElementById("gf_other_img").files;
    if (productNameRegex.test(name) === false || productPriceRegex.test(price) === false || productUnitRegex.test(unit) === false || productDescriptionRegex.test(description) === false) {
        layer.alert('存在信息格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (type === "default") {
            layer.alert('请选择商品类型！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            if (type === "6781b727493c41a2b0bfe2f9df69600e") {
                layer.alert('农场相关操作请转至“农场管理”！', {skin:'layui-layer-lan', closeBtn: 0});
            } else {
                var flagMain = true;
                var flagOther = true;

                if (mainImage.length !== 0) {
                    var suffixMain = mainImage[0].name.substring(mainImage[0].name.lastIndexOf("."));
                    if (suffixMain !== ".jpg" && suffixMain !== ".jpeg" && suffixMain !== ".gif" && suffixMain !== ".png" && suffixMain !== ".JPG" && suffixMain !== ".JPEG" && suffixMain !== ".GIF" && suffixMain !== ".PNG") {
                        layer.alert('商品主图片文件格式不符！', {skin:'layui-layer-lan', closeBtn: 0});
                        flagMain = false;
                    } else {
                        if (mainImage[0].size >= 2 * 1024 * 1024) {
                            layer.alert('商品主图片大小不能超过2MB！', {skin:'layui-layer-lan', closeBtn: 0});
                            flagMain = false;
                        } else {
                            flagMain = true;
                        }
                    }
                }

                if (otherImages.length !== 0) {
                    if (otherImages.length > 5) {
                        layer.alert('商品其他图片的数量不能超过5张！', {skin:'layui-layer-lan', closeBtn: 0});
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
                            layer.alert('商品其他图片存在文件格式不符 或 单个图片大小超过2MB！', {skin:'layui-layer-lan', closeBtn: 0});
                            flagOther = false;
                        } else {
                            if (sum >= 10 * 1024 * 1024) {
                                layer.alert('商品其他图片大小不能超过10MB！', {skin:'layui-layer-lan', closeBtn: 0});
                                flagOther = false;
                            } else {
                                flagOther = true;
                            }
                        }
                    }
                }

                if (flagMain === true && flagOther === true) {
                    layer.confirm('确定要上架该商品？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        formData.append("name", name);
                        formData.append("price", price);
                        formData.append("type", type);
                        if ($.trim(unit) !== "") {
                            formData.append("unit", unit);
                        }
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
                            url : "/business/management/product/add/operation",
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
                                } else {
                                    layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
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

$("button[name='gf_detail']").on('click', function () {
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
                    "            <form class='form-horizontal'>\n";

                var filltext1 = "<div class='control-group'>\n" +
                    "                        <label class='control-label'>";
                var filltext2 = "</label>\n" +
                    "                        <div class='controls'>\n" +
                    "                            <input name='id' value='";
                var filltext3 = "' readonly='readonly' type='text' class='m-wrap media'/>\n" +
                    "                        </div>\n" +
                    "                    </div>";


                var content2 = "";
                var listname2 = ["商品名称：", "商品单价：", "商品类型：", "商品描述：", "当前库存：", "成功上架日期："];
                var listvalue2 = [jsonData.name, jsonData.price, jsonData.type, jsonData.description, jsonData.stock, jsonData.upDate];
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

                var foot =
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
                    content : top + content2 + foot
                });
            }
        }
    });
});

$("button[name='gf_down']").on('click', function () {
    var productUid = $(this).attr("value");
    layer.confirm('确定下架该商品（下架前的订单仍然有效）？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/business/management/product/down/operation",
            dataType : "json",
            data : {
                "uid" : productUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.closeAll();
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "business/management/product");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});

$("button[name='gf_stock']").on('click', function () {
    var productUid = $(this).attr("value");
    layer.closeAll();
    var content = "<!DOCTYPE html>\n" +
        "<head>\n" +
        "    <meta charset='UTF-8'>\n" +
        "    <title>Title</title>\n" +
        "    <script type='text/javascript' src='/gfstatic/js/common/url.js'></script>\n" +
        "</head>\n" +
        "<body>\n" +
        "    <div class='row-fluid'>\n" +
        "        <div class='portlet box blue'>\n" +
        "            <div class='portlet-title'>\n" +
        "                <div class='caption'>\n" +
        "                    <i class='fa fa-reorder'></i>\n" +
        "                    发布农场租赁信息\n" +
        "                </div>\n" +
        "            </div>\n" +
        "            <div class='portlet-body'>\n" +
        "                <form class='form-horizontal'>\n" +
        "                    <div class='control-group'>\n" +
        "                        <label class='control-label'>库存：</label>\n" +
        "                        <div class='controls'>\n" +
        "                            <input id='gf_stock' type='text' class='m-wrap media'/>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "\n" +
        "                    <div class='control-group'>\n" +
        "                        <div class='controls'>\n" +
        "                            <input type='button' id='gf_stock_sure' value='确定' class='layui-btn layui-btn-normal layui-btn-ms' disabled='disabled'/>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "\n" +
        "                    <div class='control-group'>\n" +
        "                        <div class='controls'>\n" +
        "                            <a href='javascript:void(0);' name='gf_stock_nolimit' class='layui-btn layui-btn-normal layui-btn-ms'>设置为库存充足</a>\n" +
        "                            <a href='javascript:void(0);' name='gf_stock_null' class='layui-btn layui-btn-normal layui-btn-ms'>设置库存为零</a>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "\n" +
        "                    <input type='hidden' value='' + productUid + '' id='gf_product_uid'/>\n" +
        "                </form>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "\n" +
        "<script type='text/javascript' src='/gfstatic/js/management/business/product.js'></script>\n" +
        "</body>\n" +
        "</html>";
    layer.open({
        type: 1,
        skin: 'layui-layer-rim',
        area: ['420px', '240px'],
        shadeClose: true,
        content: content
    });

});

$("#gf_clear_no_pass").on('click', function () {
    layer.confirm('确定要清除未通过审核的记录？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
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

$("#gf_stock_sure").on('click', function () {
    var productUid = $("#gf_product_uid").val();
    var stock = $("#gf_stock").val();
    if (stockRegex.test(stock) === false) {
        layer.alert('库存数量格式不规范！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        layer.closeAll();
        layer.confirm('确定设置为该库存？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
            $.ajax({
                type : "POST",
                url : "/business/management/product/stock/operation",
                dataType : "json",
                data : {
                    "stock" : stock,
                    "uid" : productUid
                },
                success : function (jsonData) {
                    if (jsonData.flag === true) {
                        layer.closeAll();
                        layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                            $(location).attr("href", getPathPrefix() + "business/management/product");
                        });
                    }
                }
            });
        }, function () {
            // null operation
        });
    }
});

$("button[name='gf_stock_nolimit']").on('click', function () {
    var productUid = $("#gf_product_uid").val();
    layer.closeAll();
    layer.confirm('确定设置为库存充足？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/business/management/product/stock/nolimit/operation",
            dataType : "json",
            data : {
                "uid" : productUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.closeAll();
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "business/management/product");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});

$("button[name='gf_stock_null']").on('click', function () {
    var productUid = $("#gf_product_uid").val();
    layer.confirm('确定设置为库存为零？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/business/management/product/stock/null/operation",
            dataType : "json",
            data : {
                "uid" : productUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.closeAll();
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "business/management/product");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});