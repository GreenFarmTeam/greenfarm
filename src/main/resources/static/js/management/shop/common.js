
$.ajax({
    type:"GET",
    url:"/greenfarm/shop/load/list/classification",
    dataType:"json",
    success : function (jsonData) {

        if (jsonData.flag === 0) {
            var list = $("#category");
            $(jsonData.data).each(function (index, item) {

                var cat = $("<dd><a href='/greenfarm/shop/load/classification/"+item.typeUid+"'>" + item.typeName + "</a></dd>");
                list.append(cat);
            })
        } else {
            alert("哈哈"+data.message);
        }
    }
})

$.ajax({
    type:"GET",
    url:"/greenfarm/shop/load/list/farm/classification",
    dataType:"json",
    success : function (jsonData) {

        if (jsonData.flag === 0) {
            var list = $("#farm-category");
            $(jsonData.data).each(function (index, item) {

                var cat = $("<dd><a href='/greenfarm/shop/farm/load/classification/"+item.farmTypeUid+"'>" + item.farmTypeName + "</a></dd>");
                list.append(cat);
            })
        } else {
            alert("哈哈"+data.message);
        }
    }
})