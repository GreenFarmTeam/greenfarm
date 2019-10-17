/*$("a[name='productType']").on('click', function () {
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

});*/
var pageNo = 0;
var isMore = true;
$(function () {
    //加载热门商品
    loadHot();
    //加载最新商品
    loadNew(pageNo);
});

function loadHot() {
    $.ajax({
        type:"GET",
        url:"/member/management/product/load/all/operation",
        dataType:"json",
        success : function (jsonData) {

            if (jsonData.flag === 1) {
                var div = $("#product_hot");
                console.log(jsonData)
                appendToPage(div, jsonData.productList);


            } else {
                layer.msg("请求失败！")
            }
        }
    })
}

function loadNew(pageindex) {
    $.ajax({
        type:"GET",
        url:"/member/management/new/product/load/all/operation?pageSize=12&page=" + pageindex,
        dataType:"json",
        success : function (jsonData) {
            layer.closeAll();
            if (jsonData.flag === 1) {
                pageNo++;
                var div = $("#product_new");
                if(isMore) {
                    if (jsonData.productList.pageInfo.hasNextPage == false) {
                        appendToPage(div, jsonData.productList.memberProductVoList);
                        isMore = false;

                    } else
                        appendToPage(div, jsonData.productList.memberProductVoList);
                }else{
                    layer.msg("没有更多了！")
                }

            } else {
                layer.msg("请求失败！")
            }
        }
    })
}
//加载更多
function more() {
    var load = layer.load();
    pageNo++;
    loadNew(pageNo);
}
// 将数据渲染到页面上
function appendToPage(div, data) {

    $(data).each(function (index, item) {

        var productDiv1 = $("<div class='layui-col-md3 layui-col-xs6 product_container'></div>");
        var conetDiv = $("<a href='/member/shop/product/load/detail/operation?id="+item.product.productUid+"' style='display: block; '></a>");
        productDiv1.append(conetDiv);
        conetDiv.append($("<div class='image'><img src='"+ item.mainImage.productImagePath +"'></div>"));
        conetDiv.append($("<div class='title'>" + item.product.productName + "</div>"));
        conetDiv.append($("<div class='price'>" + item.product.productPrice + "元/"+item.product.productUnit+"</div>"));
        div.append(productDiv1);

    });
}
