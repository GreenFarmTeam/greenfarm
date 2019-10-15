/**
 * 商家详情
 */
$("button[name='detail']").on('click', function () {
    var businessId = $(this).attr("value");
    $("#mainFrame",window.parent.document).attr("src","/greenfarm/admin/management/business/detail/"+businessId);
});

/**
 * 返回之前的页面
 */
$(function(){
    $("#back").click(function(){
        $("#mainFrame",window.parent.document).attr("src","/greenfarm/admin/management/business/loadAll");
    });
});

$("button[name='gf_agree']").on('click', function () {
    var reviewUid = $(this).attr("value");
    layer.confirm('确定同意该申请？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
        $.ajax({
            type : "GET",
            url : "/greenfarm/admin/management/business/review/agree/" + reviewUid,
            dataType : "json",
            success : function (jsonData) {
                if (jsonData.flag === true) {
                    layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function() {
                        $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/business/review");
                    });
                }
            }
        });
    }, function () {
        // null operation
    });
});


$("button[name='gf_disagree']").on('click', function () {
    var reviewUid = $(this).attr("value");
    layer.confirm('确定驳回该申请？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
        layer.confirm('需要告知未审核通过理由！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function() {
            $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/business/review/disagree/" + reviewUid);
        });
    }, function () {
        // null operation
    });
});


$("button[name='gf_detail']").on('click', function () {
    var reviewUid = $(this).attr("value");
    $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/business/review/detail/" + reviewUid);
});

$("#gf_sure").on('click', function () {
    var reason = $("#gf_reason").val();
    var reviewUid = $("#gf_review_uid").val();
    if ($.trim(reason) === "") {
        layer.alert('请填写未审核通过理由！', {skin:'layui-layer-lan', closeBtn: 0});
    } else {
        if (reason.length > 125) {
            layer.alert('理由不允许超过125个字！', {skin:'layui-layer-lan', closeBtn: 0});
        } else {
            layer.confirm('最终确定驳回申请？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function() {
                $.ajax({
                    type : "POST",
                    url : "/greenfarm/admin/management/business/review/disagree/" + reviewUid + "/operation",
                    dataType : "json",
                    data : {
                        "reason" : reason
                    },
                    success : function (jsonData) {
                        if (jsonData.flag === true) {
                            layer.confirm('操作成功！', {btn:['是'], skin:'layui-layer-lan', closeBtn:0}, function() {
                                $(location).attr("href", getPathPrefix() + "greenfarm/admin/management/business/review");
                            });
                        }
                    }
                });
            }, function () {
                // null operation
            });
        }
    }
});

/**
 * 更新用户的信息
 */
// $("button[name='gf_sure']").on('click', function () {
//
//     var businessUID=$("input[name='id']").val();
//     var nickName = $("input[name='nickName']").val();
//     var shopName = $("input[name='shopName']").val();
//     var businessDescription = $("input[name='description']").val();
//
//     if (nicknameRegex.test(nickName) === false) {
//         layer.alert('昵称为数字、字母 汉字1-25位！', {skin:'layui-layer-lan', closeBtn: 0});
//     } else if(shopNameRegex.test(shopName)===false){
//         layer.alert('店铺名称为数字、字母 汉字4-15位！', {skin:'layui-layer-lan', closeBtn: 0});
//     } else if(businessShopDescriptionRegex.test(businessDescription)===false){
//         layer.alert('店铺描述为数字、字母 汉字0-255位(含标点)！', {skin:'layui-layer-lan', closeBtn: 0});
//     }
//       else
//         {
//             layer.confirm('确定要修改该商品？', {btn:['是','否'], skin:'layui-layer-lan', closeBtn:0}, function () {
//                 var formData = new FormData();
//                 formData.append("businessUID", businessUID);
//                 formData.append("nickName", nickName);
//                 formData.append("shopName", shopName);
//                 formData.append("businessDescription", businessDescription);
//                 $.ajax({
//                     type : "POST",
//                     url : "/admin/management/business/edit/operation",
//                     dataType : "json",
//                     data : formData,
//                     contentType : false,
//                     processData : false,
//                     cache : false,
//                     success : function (jsonData) {
//                         if (jsonData.flag === true) {
//                             layer.alert("修改成功！", {skin:'layui-layer-lan', closeBtn: 0});
//                             /*$(location).attr("href", getPathPrefix() + "business/management/index");*/
//
//                             $("#mainFrame",window.parent.document).attr("src","/greenfarm/admin/management/business/detail/"+jsonData.businessUID);
//                         } else {
//
//                         }
//                     }
//                 });
//             }, function () {
//                 // null operation
//             });
//     }
// });