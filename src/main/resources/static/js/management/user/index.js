jQuery(document).ready(function () {
    App.init();
});

function changePage(page) {
    $(".page-sidebar-menu li").removeClass("active");

    if (page === 'profile') {
        $("#profile").addClass("active");
        $("#mainFrame").attr("src", "/user/management/profile");
    } else if (page === 'address-manage') {
        $("#address").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#address-manage").addClass("active");
        $("#mainFrame").attr("src", "/user/management/address");
    } if (page === 'address-add') {
        $("#address").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#address-add").addClass("active");
        $("#mainFrame").attr("src", "/user/management/address/add");
    } if (page === 'business-apply') {
        $("#business").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#business-apply").addClass("active");
        $("#mainFrame").attr("src", "/user/management/business/apply");
    } if (page === 'business-apply-history') {
        $("#business").addClass("active").find("a").eq(0).append($('<span class="selected"></span>'));
        $("#business-apply-history").addClass("active");
        $("#mainFrame").attr("src", "/user/management/business/apply/history");
    }

}

changePage("profile");