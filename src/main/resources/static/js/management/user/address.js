//新增
//删除
//修改
//联动
var gfProvince = $("#gf_province");
var gfCity = $("#gf_city");
var gfDistrict = $("#gf_district");

// 市 是否还有下一级信息
var cityHasSubLevel = 1;

// 省
gfProvince.on('change', function () {
    if (gfProvince.val() === "default") {
        // 没有选择 省 ==> 市、县默认
        gfCity.val("default");
        gfCity.attr("disabled", "disabled");
        gfDistrict.val("default");
        gfDistrict.attr("disabled", "disabled");
    } else {
        // 选择 省 ==> 市变化，县默认
        gfCity.find("option").remove();
        gfCity.append($("<option value='default' selected>请选择</option>"));
        gfCity.removeAttr("disabled");
        gfCity.val("default");
        gfDistrict.val("default");
        gfDistrict.attr("disabled", "disabled");
        $.ajax({
            type : "GET",
            url : "/address/level",
            dataType : "json",
            data : {
                "pid" : gfProvince.val()
            },
            success : function (jsonData) {
                if (jsonData.status === 0) {
                    // 遍历
                    var cities = jsonData.sub;
                    // 添加至下拉列表
                    for (i in cities) {
                        var op = $("<option value='" + cities[i].id + "'>" + cities[i].name + "</option>");
                        gfCity.append(op);
                    }
                    var other = $("<option value='other'>其他</option>");
                    gfCity.append(other);
                } else {
                    if (jsonData.status === 1) {
                        gfCity.val("default");
                        gfCity.attr("disabled", "disabled");
                        gfDistrict.val("default");
                        gfDistrict.attr("disabled", "disabled");
                    }
                }
            }
        });
    }
});

// 市
gfCity.on('change', function () {
    if (gfCity.val() === "default") {
        // 没有选择 市 ==> 县默认
        gfDistrict.val("default");
        gfDistrict.attr("disabled", "disabled");
    } else {
        if (gfCity.val() === "other") {
            gfDistrict.val("default");
            gfDistrict.attr("disabled", "disabled");
        } else {
            gfDistrict.find("option").remove();
            gfDistrict.append($("<option value='default' selected>请选择</option>"));
            gfDistrict.val("default");
            gfDistrict.removeAttr("disabled");
            $.ajax({
                type: "GET",
                url: "/address/level",
                dataType: "json",
                data: {
                    "pid": gfCity.val()
                },
                success : function (jsonData) {
                    if (jsonData.status === 0) {
                        cityHasSubLevel = 1;
                        var districts = jsonData.sub;
                        for (i in districts) {
                            var op = $("<option value='" + districts[i].id + "'>" + districts[i].name + "</option>");
                            gfDistrict.append(op);
                        }
                        var other = $("<option value='other'>其他</option>");
                        gfDistrict.append(other);
                    } else {
                        if (jsonData.status === 1) {
                            cityHasSubLevel = 0;
                            gfDistrict.val("default");
                            gfDistrict.attr("disabled", "disabled");
                        }
                    }
                }
            });
        }
    }
});


// 新增收货地址
$("#gf_sure").on('click', function () {
    var name = $("#gf_name").val();
    var provinceVal = $("#gf_province").val();
    var cityVal = $("#gf_city").val();
    var districtVal = $("#gf_district").val();
    var address = $("#gf_desc").val();
    if ($.trim(name) === "" || $.trim(address) === "" || provinceVal === "default") {
        layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (addressNameRegex.test(name) === false) {
            layer.alert('收货人姓名格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            if (addressRegex.test(address) === false) {
                layer.alert('收货详细地址格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
            } else {
                // 未选择 市
                if (cityVal === "default") {
                    layer.alert('请选择收货地址所在的市！', {skin:'layui-layer-lan', closeBtn: 0});
                } else {
                    // 选择了 市，但是 市 不是 “其他”
                    if (cityVal !== "other") {
                        if (districtVal === "default") {
                            if (cityHasSubLevel === 0) {
                                layer.confirm('确定要添加收货地址？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
                                    $.ajax({
                                        type : "POST",
                                        url : "/user/management/address/add/operation",
                                        dataType : "json",
                                        data : {
                                            "name" : name,
                                            "address" : address,
                                            "province" : gfProvince.find("option:selected").text(),
                                            "city" : gfCity.find("option:selected").text()
                                        },
                                        success : function (jsonData) {
                                            if (jsonData.flag === true) {
                                                layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                                                    $(location).attr("href", getPathPrefix() + "user/management/address");
                                                });
                                            }
                                        }
                                    });
                                }, function () {
                                    // null operation
                                });
                            } else {
                                layer.alert('请选择收货地址所在的县！', {skin:'layui-layer-lan', closeBtn: 0});
                            }
                        } else {
                            console.log("OK2");
                            layer.confirm('确定要添加收货地址？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
                                $.ajax({
                                    type : "POST",
                                    url : "/user/management/address/add/operation",
                                    dataType : "json",
                                    data : {
                                        "name" : name,
                                        "address" : address,
                                        "province" : gfProvince.find("option:selected").text(),
                                        "city" : gfCity.find("option:selected").text(),
                                        "district" : gfDistrict.find("option:selected").text()
                                    },
                                    success : function (jsonData) {
                                        if (jsonData.flag === true) {
                                            layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                                                $(location).attr("href", getPathPrefix() + "user/management/address");
                                            });
                                        }
                                    }
                                });
                            }, function () {
                                // null operation
                            });
                        }
                    } else {
                        console.log("OK3");
                        layer.confirm('确定要添加收货地址？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
                            $.ajax({
                                type : "POST",
                                url : "/user/management/address/add/operation",
                                dataType : "json",
                                data : {
                                    "name" : name,
                                    "address" : address,
                                    "province" : gfProvince.find("option:selected").text(),
                                    "city" : gfCity.find("option:selected").text()
                                },
                                success : function (jsonData) {
                                    if (jsonData.flag === true) {
                                        layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                                            $(location).attr("href", getPathPrefix() + "user/management/address");
                                        });
                                    }
                                }
                            });
                        }, function () {
                            // null operation
                        });
                    }
                }
            }
        }
    }
});

// 删除收货地址
$("button[name=gf_delete]").on('click', function () {
    var uid = $(this).attr("value");
    layer.confirm('确定要删除收货地址？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
        $.ajax({
            type : "GET",
            url : "/user/management/address/remove/operation",
            dataType : "json",
            data : {
                "uid" : uid
            },
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                        $(location).attr("href", getPathPrefix() + "user/management/address");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});

// 修改收货地址
$("#gf_sure").on('click', function () {
    var name = $("#gf_name").val();
    var provinceVal = $("#gf_province").val();
    var cityVal = $("#gf_city").val();
    var districtVal = $("#gf_district").val();
    var address = $("#gf_desc").val();
    var addressUid = $("#gf_address_uid").val();

    if ($.trim(name) === "" || $.trim(address) === "" || provinceVal === "default") {
        layer.alert('请填写完整信息！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (addressNameRegex.test(name) === false) {
            layer.alert('收货人姓名格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            if (addressRegex.test(address) === false) {
                layer.alert('收货详细地址格式错误！', {skin:'layui-layer-lan', closeBtn: 0});
            } else {
                // 未选择 市
                if (cityVal === "default") {
                    layer.alert('请选择收货地址所在的市！', {skin:'layui-layer-lan', closeBtn: 0});
                } else {
                    // 选择了 市，但是 市 不是 “其他”
                    if (cityVal !== "other") {
                        if (districtVal === "default") {
                            if (cityHasSubLevel === 0) {
                                layer.confirm('确定要修改收货地址？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
                                    $.ajax({
                                        type : "POST",
                                        url : "/user/management/address/modify/" + addressUid + "/operation",
                                        dataType : "json",
                                        data : {
                                            "name" : name,
                                            "detail" : address,
                                            "province" : gfProvince.find("option:selected").text(),
                                            "city" : gfCity.find("option:selected").text()
                                        },
                                        success : function (jsonData) {
                                            if (jsonData.flag === true) {
                                                layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                                                    $(location).attr("href", getPathPrefix() + "user/management/address");
                                                });
                                            }
                                        }
                                    });
                                }, function () {
                                    // null operation
                                });
                            } else {
                                layer.alert('请选择收货地址所在的县！', {skin:'layui-layer-lan', closeBtn: 0});
                            }
                        } else {
                            console.log("OK2");
                            layer.confirm('确定要修改收货地址？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
                                $.ajax({
                                    type : "POST",
                                    url : "/user/management/address/modify/" + addressUid + "/operation",
                                    dataType : "json",
                                    data : {
                                        "name" : name,
                                        "detail" : address,
                                        "province" : gfProvince.find("option:selected").text(),
                                        "city" : gfCity.find("option:selected").text(),
                                        "district" : gfDistrict.find("option:selected").text()
                                    },
                                    success : function (jsonData) {
                                        if (jsonData.flag === true) {
                                            layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                                                $(location).attr("href", getPathPrefix() + "user/management/address");
                                            });
                                        }
                                    }
                                });
                            }, function () {
                                // null operation
                            });
                        }
                    } else {
                        console.log("OK3");
                        layer.confirm('确定要修改收货地址？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
                            $.ajax({
                                type : "POST",
                                url : "/user/management/address/modify/" + addressUid + "/operation",
                                dataType : "json",
                                data : {
                                    "name" : name,
                                    "detail" : address,
                                    "province" : gfProvince.find("option:selected").text(),
                                    "city" : gfCity.find("option:selected").text()
                                },
                                success : function (jsonData) {
                                    if (jsonData.flag === true) {
                                        layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function () {
                                            $(location).attr("href", getPathPrefix() + "user/management/address");
                                        });
                                    }
                                }
                            });
                        }, function () {
                            // null operation
                        });
                    }
                }
            }
        }
    }
});

