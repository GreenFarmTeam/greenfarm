$("a[name='gf_location']").on('click', function () {
    var lng = $(this).attr("data-lng");
    var lat = $(this).attr("data-lat");
    var content = "<!DOCTYPE html>" +
        "<html>" +
        "<head>" +
        "<meta charset='UTF-8'>" +
        "</head>" +
        "<body>" +
        "<div id='container' style='width: 600px; height: 450px'></div>" +
        "<script type='text/javascript'>" +
        "var map = new AMap.Map('container', {" +
        "zoom : 14," +
        "resizeEnable : false});" +
        "markPoint(map, " + lng + ", " + lat + ");" +
        "</script>" +
        "</body>" +
        "</html>";
    layer.open({
        type: 1,
        skin: 'layui-layer-rim',
        area: ['600px', '450px'],
        shadeClose: false,
        content: content
    });
});