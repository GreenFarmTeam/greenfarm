
/**
 * 查看会员信息
 */

$("button[name='detail']").on('click', function () {
    var memberID = $(this).attr("value");
    $("#mainFrame",window.parent.document).attr("src","/greenfarm/admin/management/member/detail/"+memberID);
});

/**
 *
 * @param id
 * @param btn
 */

$("button[name='delete']").on('click',function () {
    var memberID = $(this).attr("value");
    layer.confirm("是否删除id为"+memberID+"的用户？", {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0},function () {
        $.ajax({
            url:"/greenfarm/admin/management/member/delete/"+memberID,
            type:"get",
            dataType : "json",
            success:function(jsonData){
                if(jsonData.flags === 1){
                    layer.alert("删除成功！");
                    $("#mainFrame",window.parent.document).attr("src","/greenfarm/admin/management/member/loadAll");
                    $(btn).parent().parent().fadeOut();
                }else{
                    alert(jsonData.flags)
                    layer.alert("删除失败！");
                }
            }
        });
    },function () {

    });

})


