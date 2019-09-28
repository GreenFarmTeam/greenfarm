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