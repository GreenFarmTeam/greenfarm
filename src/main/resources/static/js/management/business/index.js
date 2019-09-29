var flag = true;

// 判断该商家是否有“农场租赁”这项业务
window.onload = function () {
    $.ajax({
        type : "GET",
        url : "/business/management/farm/can",
        dataType : "json",
        data : {
            "uid" : $("#gf_business_uid").val()
        },
        success : function (jsonData) {
            flag = jsonData.flag;
        }
    });
};

$("#gf_farm_up").on('click', function () {
    if (flag === false) {
        layer.alert('无权进行此方面的业务！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        // $(location).attr("href", getPathPrefix() + "business/management/index");
    }
});

$("#gf_farm_down").on('click', function () {
    if (flag === false) {
        layer.alert('无权进行此方面的业务！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        // $(location).attr("href", getPathPrefix() + "business/management/index");
    }
});

$("#gf_farm_submit").on('click', function () {
    if (flag === false) {
        layer.alert('无权进行此方面的业务！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        $(location).attr("href", getPathPrefix() + "business/management/farm/add");
    }
});

$("#gf_farm_review").on('click', function () {
    if (flag === false) {
        layer.alert('无权进行此方面的业务！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        $(location).attr("href", getPathPrefix() + "business/management/farm/review");
    }
});