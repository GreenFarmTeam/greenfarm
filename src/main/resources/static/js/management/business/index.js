var flag = true;
jQuery(document).ready(function () {
    App.init();
});


// 判断该商家是否有“农场租赁”这项业务
window.onload = function () {
    $.ajax({
        type : "GET",
        url : "/business/management/farm/can",
        dataType : "json",
        data : {
            "uid" : $("#gf_business_uid").val()
        },
        success : function (jsonData) {
            flag = jsonData.flag;
        }
    });
};

function changePage(page) {
    $(".page-sidebar-menu li").removeClass("active");

    if (page === 'welcome') {
        $("#index").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#welcome").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/welcome");
    } else if (page === 'business-all') {
        $("#business").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#business-all").addClass("active");
        $("#mainFrame").attr("src", "/business/management/info");
    } else if (page === 'gf_product_up') {
        $("#product").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#gf_product_up").addClass("active");
        $("#mainFrame").attr("src", "/business/management/product");
    } else if (page === 'gf_product_down') {
        $("#product").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#gf_product_down").addClass("active");
        $("#mainFrame").attr("src", "/business/management/product/down");
    } else if (page === 'apply_product_up') {
        $("#product").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#apply_product_up").addClass("active");
        $("#mainFrame").attr("src", "/business/management/product/add");
    } else if (page === 'gf_product_review') {
        $("#product").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#gf_product_review").addClass("active");
        $("#mainFrame").attr("src", "/business/management/product/review");
    } else if (page === 'gf_farm_up') {
        $("#farm").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#gf_farm_up").addClass("active");
        $("#mainFrame").attr("src", "/business/management/farm/up");
    } else if (page === 'gf_farm_down') {
        $("#farm").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#gf_farm_down").addClass("active");
        $("#mainFrame").attr("src", "/business/management/farm/down");
    } else if (page === 'gf_farm_submit') {
        $("#farm").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#gf_farm_submit").addClass("active");
        $("#mainFrame").attr("src", "/business/management/farm/add");
    } else if (page === 'gf_farm_review') {
        $("#farm").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#gf_farm_review").addClass("active");
        $("#mainFrame").attr("src", "/business/management/farm/review");
    }else if (page === 'order_product') {
        $("#order").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#order_product").addClass("active");
        $("#mainFrame").attr("src", "/business/management/order/loadAll");
    }else if (page === 'order_farm') {
        $("#order").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#order_farm").addClass("active");
        $("#mainFrame").attr("src", "/business/management/farm/order/loadAll");
    }


}

changePage("welcome");