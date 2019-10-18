var pageNo = 0;
var isMore=true;
$(function () {
    //所显现分类的类型
    var type = $("#type").val();
    pageNo++;
    loadFarm(pageNo);
});

/**
 * 加载所有的产品
 * @param pageindex
 */

function loadFarm(pageindex) {
    var cid = $("#cid").val();
    var url = "/greenfarm/shop/load/farm/classification/" + cid + "?pageSize=12&page=" + pageindex;
    $.ajax({
        type:"GET",
        url:url,
        dataType:"json",
        success : function (jsonData) {
            layer.closeAll();
            if (jsonData.flag === 0) {

                console.log(jsonData)
                pageNo++;
                var div = $("#farm_cate");
                if(isMore) {
                    if (jsonData.farmList.pageInfo.hasNextPage == false) {
                        appendToPage(div, jsonData.farmList.memberFarmVOList);
                        isMore = false;

                    } else
                        appendToPage(div, jsonData.farmList.memberFarmVOList);
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
    loadFarm(pageNo);
}

// 将数据渲染到页面上
function appendToPage(div, data) {

    $(data).each(function (index, item) {

        var productDiv1 = $("<div class='layui-col-md3 layui-col-xs6 product_container'></div>");
        var conetDiv = $("<a href='/member/shop/farm/load/detail/operation?id="+item.farm.farmUid+"' style='display: block'></a>");
        productDiv1.append(conetDiv);
        conetDiv.append($("<div class='image'><img src='"+ item.mainImage.farmImagePath +"'></div>"));
        conetDiv.append($("<div class='title'>" + item.farm.farmName + "</div>"));
        conetDiv.append($("<div class='price'>" + item.farm.farmPrice + "元/"+item.farm.farmUnit+"</div>"));
        div.append(productDiv1);

    });
}
