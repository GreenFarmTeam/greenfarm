$(function () {
    //加载购物车信息
    loadCart();
});


function loadCart() {
    $.ajax({
        type:"GET",
        url:"/user/management/cart/load",
        dataType:"json",
        success : function (jsonData) {

            if (jsonData.flag === 1) {
                var table = $("#cart");
                var addressTable = $("#address")
                if (jsonData.data.length <= 0) {
                    layer.msg("您的购物车中暂无商品，快去购物吧！");
                    $("#order_form").fadeOut();
                    return;
                }
                appendToPage(table, jsonData.data);

                addressAppendToPage(addressTable, jsonData.addressData);

            } else {
                layer.msg("请求失败！")
            }
        }
    })
}

var total = 0;

function appendToPage(table, items) {
    table.html("")
    total = 0;

    $(items).each(function (index, item) {
        var tr = $("<tr style='max-height: 200px;'></tr>");
        tr.append($("<td style=''><img class='img_responsive' src='" + item.productImage.productImagePath + "'/></td>"));
        tr.append($("<td style='text-align: left;width: 180px;'>" + item.orderItem.product.productName + "</td>"));
        tr.append($("<td >单价：" + item.orderItem.product.productPrice + "</td>"));
        tr.append($("<td name='productNum'>数量：" + item.orderItem.itemCount + "</td>"));
        tr.append($("<td name='productNum'>总价：￥" + item.orderItem.itemSum+ "</td>"));
        tr.append($("<td><button onclick='add(this,"+(item.orderItem.itemCount)+"," + item.orderItem.product.productPrice + ")' value='"+item.orderItem.product.productUid+"' class='layui-btn layui-btn-radius layui-btn-danger'>增加</button></td>"));
        tr.append($("<td><button onclick='sub(this,"+(item.orderItem.itemCount)+"," + item.orderItem.product.productPrice + ")' value='"+item.orderItem.product.productUid+"' class='layui-btn layui-btn-radius layui-btn-danger'>减少</button></td>"));
        tr.append($("<td><button onclick='remove(this," + item.orderItem.itemSum + ")' class='layui-btn layui-btn-radius layui-btn-danger' value='"+item.orderItem.product.productUid+"'>删除</button></td>"));
        table.append(tr);
        total += item.orderItem.itemSum;
    });
    $("#total").html("总价：￥" + total);
}
//移除购物车
function remove(btn, subTotal) {
    var productId = btn.value;
    layer.msg("进来了")
    $.ajax({
        type:"GET",
        url:"/user/management/cart/delete/product/"+productId,
        dataType:"json",
        success : function (jsonData) {

            if (jsonData.flag === 1) {
                layer.msg("删除成功！");
                $(btn).parent().parent().fadeOut();

                total -= subTotal;
                $("#total").html("总价：￥" + total);
            } else {
                layer.msg("请求失败！")
            }
        }
    })

}
/**增加产品数量**/
function add(btn, productNum,productPrice) {
    var productId = btn.value;
    productNum++;
    $.ajax({
        type:"GET",
        url:"/user/management/cart/alter/productNum/"+productId+"/"+productNum,
        dataType:"json",
        success : function (jsonData) {

            if (jsonData.flag === 1) {
                layer.msg("增加成功！");

                var table = $("#cart");
                appendToPage(table, jsonData.data)

                total += productPrice;

            } else {
                layer.msg("库存不足！")
            }
        }
    })
}

/**减少产品数量**/
function sub(btn,  productNum,productPrice) {
    var productId = btn.value;
    productNum--;
    if(productNum>=1){
        $.ajax({
            type:"GET",
            url:"/user/management/cart/alter/productNum/"+productId+"/"+productNum,
            dataType:"json",
            success : function (jsonData) {

                if (jsonData.flag === 1) {
                    layer.msg("减少成功！");

                    var table = $("#cart");
                    appendToPage(table, jsonData.data)

                    total -= productPrice;
                } else {
                    layer.msg("操作失败！")
                }
            }
        })

    }else{
        layer.msg("已减到最少！");
    }
}


function addressAppendToPage(table, items) {
    table.html("")
    console.log(items)
    console.log(table)

    $(items).each(function (index, item) {
        var tr = $("<tr style='max-height: 200px;'></tr>");

        tr.append($("<td style='text-align: left;width: 180px;'>收货人：" + item.addressName + "</td>"));
        tr.append($("<td name='productNum'>联系电话：" + item.addressPhone + "</td>"));
        tr.append($("<td >收货地址：" + item.addressProvince+" "+item.addressCity+" "+item.addressDistrict + "</td>"));
        tr.append($("<td >详细地址：" + item.addressDetail + "</td>"));
        tr.append($("<td><button onclick='choose(this)' value='"+item.addressUid+"' class='layui-btn layui-btn-radius layui-btn-danger'>选为收货地址</button></td>"));
        table.append(tr);
    });
}
function choose(btn) {
    var addressId = btn.value;
    $.ajax({
        type:"GET",
        url:"/user/management/address/load/"+addressId,
        dataType:"json",
        success : function (jsonData) {

            if (jsonData.flag === 1) {
                /**更新收货地址表单**/
                $("input[name='name']").val(jsonData.address.addressName)
                $("input[name='phone']").val(jsonData.address.addressPhone)
                $("input[name='addr']").val(jsonData.address.addressProvince+jsonData.address.addressCity+jsonData.address.addressDistrict);
                $("input[name='detail-addr']").val(jsonData.address.addressDetail)

            } else {
                layer.msg("地址查询失败！")
            }
        }
    })

}

/**
 * 提交订单
 */
$("#gf_sure").on('click', function () {
    var formData = new FormData();
    var name = $("input[name='name']").val();
    var phone = $("input[name='phone']").val();
    var addr = $("input[name='addr']").val();
    var detailAddr = $("input[name='detail-addr']").val();
    var detailAddr = $("input[name='detail-addr']").val();
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
