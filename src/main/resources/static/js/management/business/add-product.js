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