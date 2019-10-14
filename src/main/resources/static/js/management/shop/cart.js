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
function submit_btn (btn) {
    layer.msg("我进来了!")
    var formData = new FormData();
    var name = $("input[name='name']").val();
    var phone = $("input[name='phone']").val();
    var addr = $("input[name='addr']").val();
    var detailAddr = $("input[name='detail-addr']").val();
    var totalPrice = total;

    if (addressNameRegex.test(name) === false ) {
        layer.alert('收货人存在信息格式错误(汉字2-255)！', {skin:'layui-layer-lan', closeBtn: 0});
    }
    else if(addressPhoneRegex.test(phone) == false ) {
        layer.alert('联系电话存在信息格式错误(11位)！', {skin:'layui-layer-lan', closeBtn: 0});
    }
    else if(!(detailAddr.length>2 && detailAddr.length<125)) {
        layer.alert('详细地址存在信息格式错误(汉字2-255)！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        layer.confirm('确定提交订单？', {btn: ['是', '否'], skin: 'layui-layer-lan', closeBtn: 0}, function () {
            formData.append("name", name);
            formData.append("phone", phone);
            formData.append("addr", addr);
            formData.append("detailAddr", detailAddr);
            formData.append("totalPrice", totalPrice);
            $.ajax({
                type: "post",
                url: "/member/management/order/add/operation",
                dataType: "json",
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success: function (jsonData) {
                    if (jsonData.flag === 1) {
                        layer.msg("订单提交成功！")
                        $(location).attr("href", getPathPrefix() + "member/management/toCart.html");
                    }
                }
            });

        }, function () {
            // null operation
        });
    }
};
