// 先引入：<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=f48fcde49f6822707df87e6e395b4d08"></script>

// 初始化地图
// 参数：地图的容器，一般是<div>，通过id去选择
function initMap(idContainer) {
    var map = new AMap.Map(idContainer, {
        resizeEnable : true,
        zoom : 15
    });

    map.plugin('AMap.Geolocation', function () {
        var geolocation = new AMap.Geolocation({
            enableHighAccuracy : true,
            timeout : 10000,
            buttonOffset : new AMap.Pixel(10, 20),
            zoomToAccuracy : true,
            buttonPosition : 'RB'
        });
        geolocation.getCurrentPosition();
        AMap.event.addListener(geolocation, 'complete', onComplete);
        AMap.event.addListener(geolocation, 'error', onError);

        function onComplete (data) {
            map.setCenter(new AMap.LngLat(data.position.lng, data.position.lat));
        }

        function onError(data) {
            // null operation
        }
    });

    return map;
}

// 点击地图标点 且 获取点的经纬度
// 参数：地图 map
// 参数：存储经度的控件的id
// 参数：存储纬度的控件的id
function markPointByClick(map, lngSave, latSave) {
    map.on('click', function (e) {
        var lng = e.lnglat.getLng();
        var lat = e.lnglat.getLat();

        // 标点之前清空之前所标注的点
        map.clearMap();

        // 使地图的中心移动到标点处
        map.setCenter(new AMap.LngLat(lng, lat));

        // 标点
        var marker = new AMap.Marker({
            position : new AMap.LngLat(lng, lat)
        });
        map.add(marker);
        $("#" + lngSave).val(lng);
        $("#" + latSave).val(lat);
    });
}

// 回显时候使用
function markPoint(map, lng, lat) {
    // 标点之前清空之前所标注的点
    map.clearMap();

    // 使地图的中心移动到标点处
    map.setCenter(new AMap.LngLat(lng, lat));

    // 标点
    var marker = new AMap.Marker({
        position : new AMap.LngLat(lng, lat)
    });
    map.add(marker);
}


function initMap2(containerId) {

    var map = new AMap.Map(containerId, {
        resizeEnable : true,
        zoom : 14
    });

    AMap.plugin('AMap.Geolocation', function() {
        var geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,
            timeout: 10000,
            buttonPosition:'RB',
            buttonOffset: new AMap.Pixel(10, 20),
            zoomToAccuracy: true

        });
        map.addControl(geolocation);
        geolocation.getCurrentPosition(function(status,result){
            if(status === 'complete'){
                onComplete(result);
            }else{
                onError(result);
            }
        });
    });

    //解析定位结果
    function onComplete(data) {
        $("#now-lng").val(data.position.lng);
        $("#now-lat").val(data.position.lat);
    }
    //解析定位错误信息
    function onError(data) {

    }

    return map;

}

var map = new AMap.Map('show_map', {
    resizeEnable : true,
    zoom : 15
});
markPoint(map, $("#gf_lng").val(), $("#gf_lat").val());

$("button[name='gf_location']").on('click', function () {
    var lng = $(this).attr("data-lng");
    var lat = $(this).attr("data-lat");
    var content = "<!DOCTYPE html>" +
        "<html>" +
        "<head>" +
        "<meta charset='UTF-8'>" +
        "</head>" +
        "<body>" +
        "<div id='container' style='width: 800px; height: 600px'></div>" +
        "<script type='text/javascript'>" +
        "var map = initMap2('container');" +
        "map.setCenter(new AMap.LngLat(" + lng + ", " + lat + "));" +
        "var localLng = $('#now-lng').val();" +
        "var localLat = $('#now-lat').val();" +
        "var driving = new AMap.Driving({" +
        "map:map" +
        "});" +
        "driving.search(new AMap.LngLat(localLng, localLat), new AMap.LngLat(" + lng + ", " + lat + "), function(status, result) {});" +
        "</script>" +
        "</body>" +
        "<ml>";
    layer.open({
        type: 1,
        skin: 'layui-layer-rim',
        area: ['800px', '600px'],
        shadeClose: false,
        content: content
    });
});
