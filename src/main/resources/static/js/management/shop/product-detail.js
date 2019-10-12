function addCart() {

    var productId = $("#productId").val();
    console.log(productId)
    if(!productId){
        layer.msg("错误！");
        return;
    }
    $.ajax({
        type:"GET",
        url:"/user/management/cart/add/product/"+productId,
        dataType:"json",
        success : function (jsonData) {

            if (jsonData.flag === 1) {
                layer.msg("添加购物车成功！");

            } else {
                layer.msg("购物车中已有该商品！")
            }
        }
    })
}


//移动端页面优化
var device = layui.device();
if (device.weixin || device.ios || device.android){
    $(".product_info p").attr("style","margin-left: 0;margin-bottom: 0;");
    $(".layui-btn").attr("style","width:100%");
    $(".size1").addClass("price2").removeClass("size1");
    $(".size2").addClass("price2").removeClass("size2");
    $(".size3").addClass("price2").removeClass("size3");
}