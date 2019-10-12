jQuery(document).ready(function () {
    App.init();
});

function changePage(page) {
    $(".page-sidebar-menu li").removeClass("active");

    if (page === 'welcome') {
        $("#index").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#welcome").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/welcome");
    } else if (page === 'business-all') {
        $("#business").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#business-all").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/business/loadAll");
    } else if (page === 'business-review') {
        $("#business").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#business-review").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/business/review");
    } else if (page === 'product-up') {
        $("#product").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#product-up").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/product/up");
    } else if (page === 'product-down') {
        $("#product").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#product-down").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/product/down");
    } else if (page === 'product-review') {
        $("#product").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#product-review").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/product/review");
    } else if (page === 'farm-up') {
        $("#farm").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#farm-up").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/farm/up");
    } else if (page === 'farm-down-admin') {
        $("#farm").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#farm-down").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/farm/down");
    } else if (page === 'farm-review') {
        $("#farm").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#farm-review").addClass("active");
        $("#mainFrame").attr("src", "/greenfarm/admin/management/farm/review");
    } else {
        
    }
    
}

changePage("welcome");