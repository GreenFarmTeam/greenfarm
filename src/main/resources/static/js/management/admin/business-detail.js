/**
 * 返回之前的页面
 */
$(function(){
    $("#back").click(function(){
        $("#mainFrame",window.parent.document).attr("src","/greenfarm/admin/management/business/loadAll");
    });
});

/**
 * 更新用户的信息
 */
$("button[name='gf_sure']").on('click', function () {

    var businessUID=$("input[name='id']").val();
    var nickName = $("input[name='nickName']").val();
    var shopName = $("input[name='shopName']").val();
    var businessDescription = $("input[name='description']").val();

    if (nicknameRegex.test(nickName) === false) {
        layer.alert('昵称为数字、字母 汉字1-25位！', {skin:'layui-layer-lan', closeBtn: 0});
    } else if(shopNameRegex.test(shopName)===false){
        layer.alert('店铺名称为数字、字母 汉字4-15位！', {skin:'layui-layer-lan', closeBtn: 0});
    } else if(businessShopDescriptionRegex.test(businessDescription)===false){
        layer.alert('店铺描述为数字、字母 汉字0-255位(含标点)！', {skin:'layui-layer-lan', closeBtn: 0});
    }
      else
        {
            layer.confirm('确定要修改该商品？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
                var formData = new FormData();
                formData.append("businessUID", businessUID);
                formData.append("nickName", nickName);
                formData.append("shopName", shopName);
                formData.append("businessDescription", businessDescription);
                $.ajax({
                    type : "POST",
                    url : "/admin/management/business/edit/operation",
                    dataType : "json",
                    data : formData,
                    contentType : false,
                    processData : false,
                    cache : false,
                    success : function (jsonData) {
                        if (jsonData.flag === true) {
                            layer.alert("修改成功！", {skin:'layui-layer-lan', closeBtn: 0});
                            /*$(location).attr("href", getPathPrefix() + "business/management/index");*/

                            $("#mainFrame",window.parent.document).attr("src","/greenfarm/admin/management/business/detail/"+jsonData.businessUID);
                        } else {

                        }
                    }
                });
            }, function () {
                // null operation
            });
    }
});