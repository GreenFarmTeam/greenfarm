var pageNo = 0;
var isMore=true;
$(function () {
    //所显现分类的类型
    var type = $("#type").val();
    pageNo++;
    loadProduct(pageNo);
});

/**
 * 加载所有的产品
 * @param pageindex
 */

function loadProduct(pageindex) {
    var cid = $("#cid").val();
    var url = "/greenfarm/shop/load/products/" + cid + "?pageSize=12&page=" + pageindex;


    $.ajax({
        type:"GET",
        url:url,
        dataType:"json",
        success : function (jsonData) {
            layer.closeAll();
            if (jsonData.flag === 0) {
                /*console.log(jsonData.productList.memberProductVoList)
                console.log(jsonData)*/
                pageNo++;
                var div = $("#product_cate");
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

function more() {
    loadProduct(pageNo);
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
