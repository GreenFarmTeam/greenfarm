$("a[name='productType']").on('click', function () {
    $.ajax({
        type : "GET",
        url : "/member/shop/load/products/operation",
        dataType : "json",
        data : {
            "key" : key,
            "password" : password,
            "vcode" : vcode
        },
        success : function (jsonData) {
            if (jsonData.flag === false) {
                layer.alert(jsonData.reason, {skin:'layui-layer-lan', closeBtn: 0});
                refreshImageVcode();
            } else {
                $(location).attr("href", getPathPrefix() + "index");
            }
        }
    });

});
