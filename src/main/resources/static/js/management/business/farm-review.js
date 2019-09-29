$("a[name='gf_location']").on('click', function () {
    layer.closeAll();
    var lng = $(this).attr("data-lng");
    var lat = $(this).attr("data-lat");
    var content = "<!DOCTYPE html>" +
        "<html>" +
        "<head>" +
        "<script type='text/javascript' src='/gfstatic/webjars/jquery/3.4.1/jquery.min.js'></script>" +
        "<script type='text/javascript' src='/gfstatic/templateone/res/layui/layui.all.js'></script>" +
        "<script type='text/javascript' src='https://webapi.amap.com/maps?v=1.4.15&key=f48fcde49f6822707df87e6e395b4d08'></script>" +
        "<script type='text/javascript' src='/gfstatic/js/common/map.js'></script>" +
        "</head>" +
        "<body>" +
        "<div id='container' style='width: 600px; height: 450px'></div>" +
        "</body>" +
        "</html>";
    layer.open({
        type: 1,
        skin: 'layui-layer-rim',
        area: ['600px', '450px'],
        shadeClose: true,
        content: content
    });
});