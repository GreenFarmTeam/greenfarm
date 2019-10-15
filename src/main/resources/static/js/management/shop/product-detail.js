function addCart() {

    var productId = $("#productId").val();

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

/**
 * 立即购买
 */
function purchaseProduct(){
    var productId = $("#productId").val();
    if(!productId){
        layer.msg("错误！");
        return;
    }

    $.ajax({
        type:"get",
        url:"/member/management/product/purchase/"+productId,
        dataType:"json",
        success : function (jsonData) {


            var content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>支付</title>\n" +
                "</head>\n" +
                "<body>"+"<form class=\"layui-form\" action=\"\" lay-filter=\"example\">\n" +
                "  <div class=\"layui-input-block layui-row\">\n" +
                "  </div>\n" +
                "  <div class=\"layui-form-item layui-row\">\n" +
                "    <label class=\"layui-form-label layui-col-md2\">收货地址</label>\n" +
                "    <div class=\"layui-input-block layui-col-md9\">\n" +
                "      <select name=\"interest\" lay-filter=\"aihao\">\n" +
                "        <option value=\"\"></option>\n" +
                "        <option value=\"0\">写作</option>\n" +
                "        <option value=\"1\">阅读</option>\n" +
                "        <option value=\"2\">游戏</option>\n" +
                "        <option value=\"3\">音乐</option>\n" +
                "        <option value=\"4\">旅行</option>\n" +
                "      </select>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "  \n" +
                "  <div class=\" layui-input-block layui-row\">\n" +
                "  </div>\n" +
                "  <div class=\"layui-form-item layui-row\">\n" +
                "    <label class=\"layui-form-label layui-col-md2\">数量</label>\n" +
                "    <div class=\"layui-input-block layui-col-md9\">\n" +
                "      <input type=\"number\" name=\"password\"   class=\"layui-input\">\n" +
                "    </div>\n" +
                "  </div>\n" +
                "  <div class=\" layui-input-block layui-row\">\n" +
                "  </div>\n" +

                "  <div class=\"layui-form-item layui-form-text layui-row\">\n" +
                "    <label class=\"layui-form-label layui-col-md2\">备注</label>\n" +
                "    <div class=\"layui-input-block layui-col-md9\">\n" +
                "      <textarea placeholder=\"请输入内容\" class=\"layui-textarea\" name=\"desc\"></textarea>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "  <div class=\"layui-input-block layui-row\">\n" +
                "  </div>\n" +
                " \n" +
                "  <div class=\"layui-form-item\">\n" +
                "    <div class=\"layui-input-block\">\n" +
                "      <button type=\"button\" class=\"layui-btn layui-btn-normal\" id=\"LAY-component-form-setval\">赋值</button>\n" +
                "      <button type=\"button\" class=\"layui-btn layui-btn-normal\" id=\"LAY-component-form-getval\">取值</button>\n" +
                "      <button type=\"submit\" class=\"layui-btn\" lay-submit=\"\" lay-filter=\"demo1\">立即提交</button>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</form>"+"</body>\n" +
                "</html>"

            layer.msg("立即购买")
            layer.closeAll();
            layer.open({
                type: 1,
                skin: 'layui-layer-rim',
                area: ['800px', '600px'],
                shadeClose: true,
                content : content
            });
            layui.use('form', function(){
                var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
                //但是，如果你的HTML是动态生成的，自动渲染就会失效
                //因此你需要在相应的地方，执行下述方法来进行渲染
                form.render();
            });
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