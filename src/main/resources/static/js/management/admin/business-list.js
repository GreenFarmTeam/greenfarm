/**
 * 商家详情
 */
$("button[name='detail']").on('click', function () {
    var businessId = $(this).attr("value");
    $("#mainFrame",window.parent.document).attr("src","/greenfarm/admin/management/business/detail/"+businessId);
});