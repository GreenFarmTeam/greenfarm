jQuery(document).ready(function () {

    App.init(); // initlayout and core plugins
})
function changePage(page) {
    $(".page-sidebar-menu li").removeClass("active");
    if ('member' == page) {
        $("#user").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#member").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/member/loadAll");
        return;
    } else if ('business' == page) {
        $("#user").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#business").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/business/loadAll");
        return;
    } else if('business-check' == page){
        $("#privilege").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#business-check").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/business/review");
        return;

    } else if('member-check' == page){
        $("#privilege").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#member-check").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/illegal/member/loadAll");
        return;
    }
    else if('checked-farm' == page){
        $("#farm").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#checked-farm").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/farm");
        return;

    }
    else if('upping-farm' == page){
        $("#farm").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#upping-farm").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/farm/up");
        return;

    }
    else if('downing-farm' == page){
        $("#farm").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#downing-farm").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/farm/down");
        return;


    }
    else if('farm-check' == page){
        $("#farm").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#farm-check").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/farm/review");
        return;
    }
    else if('upping-product' == page){
        $("#product").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#upping-product").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/product");
        return;

    }
    else if('downing-product' == page){
        $("#product").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#downing-product").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/product/down");
        return;

    }
    else if('product-check' == page){
        $("#product").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#product-check").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/product/review");
        return;

    }
    else {
        $("#" + page).addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
    }
    $("#mainFrame").attr("src", "/admin/"+page + "/toList.html");
}
changePage("member");