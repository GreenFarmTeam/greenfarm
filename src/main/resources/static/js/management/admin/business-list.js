/**
 * 修改商家个人信息
 */
$("button[name='edit']").on('click', function () {
    var businessId = $(this).attr("value");
    $("#mainFrame",window.parent.document).attr("src","/greenfarm/admin/management/business/detail/"+businessId);
});