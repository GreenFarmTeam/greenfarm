function order() {

    var farmId = $("#farmId").val();

    if(!farmId){
        layer.msg("错误！");
        return;
    }
    $.ajax({
        type:"get",
        url:"/member/management/order/farm/"+farmId,
        dataType:"json",
        success : function (jsonData) {

            var content = "" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "   <title>支付</title>" +
                "  </head>\n" +
                " <body>\n" +
                "<form class=\"layui-form\">\n" +
                "  <div class=\"layui-input-block layui-row\">\n" +
                "  </div>\n" +"" +

                "<input type='hidden' name='realCode' />"+
                "<input type='hidden' name='farmId' value=\""+farmId+"\"/>"+

                "  <div class=\"layui-form-item\">\n" +
                "    <label class=\"layui-form-label layui-col-md4 \" style='padding: 9px 0px;margin-right: 26px'>预约人姓名</label>\n" +
                "    <div class=\"layui-input-block layui-col-md8 \" style='margin-left: 0px'>\n" +
                "      <input type=\"text\" name=\"title\" lay-verify=\"title\" autocomplete=\"off\" placeholder=\"请输入预约人姓名\" class=\"layui-input\">\n" +
                "    </div>\n" +
                "  </div>\n" +

                "  <div class=\"layui-form-item\">\n" +
                "    <div class=\"layui-inline \">\n" +
                "      <label class=\"layui-form-label layui-col-md4\" style='padding: 9px 0px ;margin-right: 26px'>联系电话</label>\n" +
                "      <div class=\"layui-input-inline \" >\n" +
                "        <input type=\"tel\" id='gf_phone' name=\"phone\" lay-verify=\"required|phone\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "      </div>\n" +
                "    <div class=\"layui-inline\">\n" +
                "      <label class=\"layui-form-label \">验证码</label>\n" +
                "      <div class=\"layui-input-inline \">\n" +
                "        <input type=\"text\" name=\"code\" lay-verify=\"url\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "      </div>\n" +
                "    </div>\n" +
                "    <div class=\"layui-inline\">\n" +
                "      <div class=\"layui-input-inline \">\n" +
                "        <button type=\"button\" id='gf_get_vcode' onclick='getCode()' class=\"layui-btn\">获取验证码" +"</button>\n"+
                "      </div>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "  <div class=\"layui-form-item\">\n" +
                "      <label class=\"layui-form-label layui-col-md4\" style='padding: 9px 0px;margin-right: 26px '>预约日期</label>\n" +
                "      <div class=\"layui-input-block layui-col-md8 \"style='margin-left: 0px'>\n" +
                "        <input type=\"text\" id=\"date\" name='order-date' lay-verify=\"date\" placeholder=\"yyyy-MM-dd\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "      </div>\n" +
                "  </div>" +

                "  <div class=\"layui-input-block layui-row\">\n" +
                "  </div>\n" +"" +
                "\n" +
                "  <div class=\"layui-form-item layui-form-text\">\n" +
                "    <label class=\"layui-form-label layui-col-md4\" style='padding: 9px 0px ;margin-right: 26px'>需求留言</label>\n" +
                "    <div class=\"layui-input-block layui-col-md8\" style='margin-left: 0px'>\n" +
                "      <textarea placeholder=\"请输入需求留言内容，没有请填无\" name='requireInfo' class=\"layui-textarea\"></textarea>\n" +
                "    </div>\n" +
                "  </div>\n" +

                "  <div class=\"layui-form-item\">\n" +
                "    <div class=\"layui-input-block\">\n" +
                "      <button class=\"layui-btn\" type='button' onclick='submit_btn(this)' lay-filter=\"demo1\">立即提交</button>\n" +
                "      <button type=\"reset\"  class=\"layui-btn layui-btn-primary\">重置</button>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</form>" +
                "</body>" +
                "<script type='text/javascript'>" +
                    /**
                     * 提交表单
                     */
                    function submit_btn(btn){
                        layer.msg("我进来了!")
                        var formData = new FormData();
                        var phone =  $("input[name='phone']").val();
                        var name = $("input[name='name']").val();
                        var code = $("input[name='code']").val();
                        var orderDate  = $("input[name='order-date']").val();
                        var requireInfo = $("textarea[name='requireInfo']").val();
                        var farmId  = $("input[name='farmId']").val();
                        var realCode = $("input[name='realCode']").val()
                        if ($.trim(phone) === "" || phoneRegex.test(phone) === false) {

                            layer.alert('手机号格式错误！', {skin:'layui-layer-lan', closeBtn: 0});

                        }else if(nicknameRegex.test(name)==false){
                            layer.alert('名字格式有误(汉字最少两位)!', {skin:'layui-layer-lan', closeBtn: 0});

                        }else if(code != realCode){

                            layer.alert('手机验证码不对!', {skin:'layui-layer-lan', closeBtn: 0});
                        }
                        else{
                            layer.confirm('确定提交订单？', {btn: ['是', '否'], skin: 'layui-layer-lan', closeBtn: 0}, function () {
                                formData.append("name", name);
                                formData.append("phone", phone);
                                formData.append("orderDate", orderDate);
                                formData.append("requireInfo", requireInfo);
                                formData.append("farmId", farmId);
                                $.ajax({
                                    type: "post",
                                    url: "/member/management/order/farm/submit",
                                    dataType: "json",
                                    data: formData,
                                    contentType: false,
                                    processData: false,
                                    cache: false,
                                    success: function (jsonData) {
                                        if (jsonData.flag == true) {
                                            layer.msg("预约成功,请前往订单中心付款！")
                                            $(location).attr("href", getPathPrefix() + "/member/shop/farm/load/detail/operation?id="+item.farm.farmUid);
                                            layer.closeAll();
                                        }else{
                                            layer.msg("预约失败，请稍后重试")
                                        }
                                    }
                                });

                            }, function () {
                                // null operation
                            });
                        }

                    }

            +

            "</script>\n"+

                "<script type='text/javascript'>" +
                /**
                 * 获取验证码
                 */
                function getCode() {
                    var timer = 70;
                            var phone = $("#gf_phone").val();
                            if ($.trim(phone) === "" || phoneRegex.test(phone) === false) {
                                layer.alert('手机号格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
                            } else {
                                layer.load(0, {shade : 0.5});
                                $.ajax({
                                    type : "POST",
                                    url : "/member/management/message/vcode",
                                    dataType : "json",
                                    data : {
                                        "phone" : phone
                                    },
                                    success : function (jsonData) {
                                        layer.closeAll('loading');
                                        if (jsonData.flag === true) {
                                            layer.alert('已向' + jsonData.phone + '成功发送验证码，请及时接收，3分钟内有效！', {skin:'layui-layer-lan', closeBtn: 0});
                                            $("#gf_get_vcode").attr("disabled", "disabled");
                                            layer.msg(timer + "s后可以重新获取动态密码");
                                            $("input[name='realCode']").val(jsonData.realCode);
                                            countDown();
                                        } else {
                                            layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                                        }
                                    }
                                });
                            }
                        function countDown() {
                            timer--;
                          /* layer.msg(timer + "s后可以重新获取动态密码");*/
                            if (timer >= 1) {
                                setTimeout(countDown, 1000);
                            } else {
                                timer = 70;
                                $("#gf_get_vcode").val("获取动态密码").removeAttr("disabled");
                            }
                    }
                }


                +
                "</script>"+

            "</html>"

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
            layui.use(['form', 'layedit', 'laydate'], function(){
                var form = layui.form
                    ,layer = layui.layer
                    ,layedit = layui.layedit
                    ,laydate = layui.laydate;

                //日期
                laydate.render({
                    elem: '#date'
                });
                laydate.render({
                    elem: '#date1'
                });

                form.render();
            });


        }
    })
/*    $.ajax({
        type:"GET",
        url:"/user/management/order/farm/"+farmId,
        dataType:"json",
        success : function (jsonData) {

            if (jsonData.flag === 1) {
                layer.msg("添加购物车成功！");

            } else {
                layer.msg("购物车中已有该商品！")
            }
        }
    })*/
}