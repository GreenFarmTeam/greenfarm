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
            var selectContent ="";
            $(jsonData.addressList).each(function (index, item) {
                selectContent=selectContent+
                    "<option value=\""+item.addressName+"|"+item.addressPhone+"|"+item.addressProvince+item.addressCity+item.addressDistrict+"|"+item.addressDetail+"\"> " +
                    item.addressName   +"   "+ +item.addressPhone+"  "+item.addressProvince+""+item.addressCity+""+item.addressDistrict+"|"+item.addressDetail
                    "</option>\n";

            });
            var productPrice = jsonData.product.productPrice;
            var productStock = jsonData.product.productStock;
            var productUid = jsonData.product.productUid;
            var content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>支付</title>\n" +
                "</head>\n" +
                "<body>"+"<form class=\"layui-form\"  method=\"post\">\n" +
                "  <div class=\"layui-input-block layui-row\">\n" +
                "  </div>\n" +"" +

                "<input type='hidden' name='prtPrice' value=\""+productPrice+"\"/>"+
                "<input type='hidden' name='prtStock' value=\""+productStock+"\"/>"+
                "<input type='hidden' name='productUid' value=\""+productUid+"\"/>"+
                "  <div class=\"layui-form-item layui-row\">\n" +

                "    <label class=\"layui-form-label layui-col-md2\">收货地址</label>\n" +

                "    <div class=\"layui-input-block layui-col-md9\">\n" +
                "      <select name=\"address\" lay-filter=\"aihao\" required=\"required\" lay-verify=\"required\">\n" +
                            selectContent+
                "      </select>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "  \n" +
                "  <div class=\" layui-input-block layui-row\">\n" +
                "  </div>\n" +
                "  <div class=\"layui-form-item layui-row\">\n" +
                "    <label class=\"layui-form-label layui-col-md2\">数量</label>\n" +
                "    <div class=\"layui-input-block layui-col-md9\">\n" +
                "      <input type=\"number\" name=\"number\" required=\"required\" lay-verify=\"required\"  class=\"layui-input\">\n" +
                "    </div>\n" +
                "  </div>\n" +

                "  <div class=\" layui-input-block layui-row\">\n" +
                "  </div>\n" +

                "  <div class=\"layui-form-item layui-form-text layui-row\">\n" +
                "    <label class=\"layui-form-label layui-col-md2\">备注</label>\n" +
                "    <div class=\"layui-input-block layui-col-md9\">\n" +
                "      <textarea placeholder=\"请输入内容,没有请填无\" class=\"layui-textarea\" required=\"required\" lay-verify=\"required\" name=\"desc\"></textarea>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "  <div class=\"layui-input-block layui-row\">\n" +
                "  </div>\n" +
                " \n" +
                "  <div class=\"layui-form-item\">\n" +
                "    <div class=\"layui-input-block\">\n" +
                "      <button onclick=\"submit_btn(this)\" type=\"button\" class=\"layui-btn\">提交订单</button>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</form>"+
                "</body>\n" +"" +
                        "<script type='text/javascript'>" +
                        /**
                         * 提交表单
                         */
                        function submit_btn(btn){
                            layer.msg("我进来了!")
                            var formData = new FormData();
                            var address = $("select[name='address']").val();
                            var prtNumber = $("input[name='number']").val();
                            var prtStock = $("input[name='prtStock']").val();
                            var prtUid  = $("input[name='productUid']").val();

                            var addressArr = address.split("|");
                            console.log(addressArr);
                            var detailAddr = addressArr.pop();
                            var addr = addressArr.pop();
                            var phone = addressArr.pop();
                            var name = addressArr.pop();
                            var totalPrice =prtNumber*$("input[name='prtPrice']").val();

                           /* $(jsonData.addressList).each(function (index, item) {
                                if(item.addressUid==addressId){
                                    name = item.addressName;
                                    phone = item.addressPhone;
                                    addr = item.addressProvince+""+item.addressCity+""+item.addressDistrict;
                                    detailAddr = item.addressDetail;
                                }
                            })*/
                           console.log(prtNumber)
                            if (prtNumber<=0 ||prtNumber==null) {
                                layer.alert('请输入合法数字(大于0)！', {skin:'layui-layer-lan', closeBtn: 0});
                            }else if(prtNumber>prtStock){
                                layer.alert('库存不足！当前存货为'+prtStock+'', {skin:'layui-layer-lan', closeBtn: 0});
                            }
                            else{
                                layer.confirm('确定提交订单？', {btn: ['是', '否'], skin: 'layui-layer-lan', closeBtn: 0}, function () {
                                    formData.append("name", name);
                                    formData.append("phone", phone);
                                    formData.append("addr", addr);
                                    formData.append("detailAddr", detailAddr);
                                    formData.append("totalPrice", totalPrice);
                                    formData.append("prtUid", prtUid);
                                    formData.append("prtNumber", prtNumber);
                                    $.ajax({
                                        type: "post",
                                        url: "/member/management/product/purchase/operation",
                                        dataType: "json",
                                        data: formData,
                                        contentType: false,
                                        processData: false,
                                        cache: false,
                                        success: function (jsonData) {
                                            if (jsonData.flag === 1) {
                                                layer.msg("订单提交成功,请前往订单中心付款！")
                                                $(location).attr("href", getPathPrefix() + "member/shop/product/load/detail/operation?id="+prtUid);
                                                layer.closeAll();
                                            }else{
                                                layer.msg(jsonData.result)
                                            }
                                        }
                                    });

                                }, function () {
                                    // null operation
                                });
                            }

                        }+
                    "</script>"+

                "</html>"

            layer.msg("立即购买")
            layer.closeAll();
            layer.open({
                type: 1,
                skin: 'layui-layer-rim',
                area: ['1000px', '600px'],
                shadeClose: true,
                content : content
            });

            /**
             * 动态加载form组件
             */
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