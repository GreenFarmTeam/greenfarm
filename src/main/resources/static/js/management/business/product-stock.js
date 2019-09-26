$("#gf_stock_sure").on('click', function () {
    var productUid = $("#gf_product_uid").val();
    var stock = $("#gf_stock").val();
    if (stockRegex.test(stock) === false) {
        layer.alert('库存数量格式不规范！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        layer.closeAll();
        layer.confirm('确定设置为该库存？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
            $.ajax({
                type : "POST",
                url : "/business/management/product/stock/operation",
                dataType : "json",
                data : {
                    "stock" : stock,
                    "uid" : productUid
                },
                success : function (jsonData) {
                    if (jsonData.flag === true) {
                        layer.closeAll();
                        layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                            $(location).attr("href", getPathPrefix() + "business/management/product");
                        });
                    }
                }
            });
        }, function () {
            // null operation
        });
    }
});


$("a[name='gf_stock_nolimit']").on('click', function () {
    var productUid = $("#gf_product_uid").val();
    layer.closeAll();
    layer.confirm('确定设置为库存充足？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/business/management/product/stock/nolimit/operation",
            dataType : "json",
            data : {
                "uid" : productUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.closeAll();
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "business/management/product");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});


$("a[name='gf_stock_null']").on('click', function () {
    var productUid = $("#gf_product_uid").val();
    layer.confirm('确定设置为库存为零？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/business/management/product/stock/null/operation",
            dataType : "json",
            data : {
                "uid" : productUid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.closeAll();
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "business/management/product");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});