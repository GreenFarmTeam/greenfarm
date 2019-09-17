$("#gf_sure").on('click', function () {
    var arrayIndex = 0;
    var shopName = $("#gf_shop_name").val();
    var shopDescription = $("#gf_shop_desc").val();
    var shopScopes = new Array();
    if ($.trim(shopName) === "" || $.trim(shopDescription) === "" || $("input[name='gf_type']:checked").length === 0) {
        layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (shopNameRegex.test(shopName) === false) {
            layer.alert('店铺名称格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            if (shopDescription.length > 125) {
                layer.alert('店铺描述不允许超过125个字！', {skin:'layui-layer-lan', closeBtn: 0});
            } else {
                $("input[name='gf_type']:checked").each(function (index, item) {
                    shopScopes[arrayIndex] = item.value;
                    arrayIndex++;
                });
                layer.confirm('确定要提交申请？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
                    $.ajax({
                        type : "POST",
                        url : "/user/management/business/apply/operation",
                        dataType : "json",
                        data : {
                            "shopName" : shopName,
                            "shopDescription" : shopDescription,
                            "shopScopes" : JSON.stringify(shopScopes)
                        },
                        success : function (jsonData) {
                            if (jsonData.flag === true) {
                                layer.confirm('申请已提交成功！请等待相关人员审核！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                                    $(location).attr("href", getPathPrefix() + "user/management/index");
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
});