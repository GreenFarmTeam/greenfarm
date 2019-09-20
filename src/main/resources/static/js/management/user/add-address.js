var gfProvince = $("#gf_province");
var gfCity = $("#gf_city");
var gfDistrict = $("#gf_district");

gfProvince.on('change', function () {
    if (gfProvince.val() === "default") {
        gfCity.val("default");
        gfCity.attr("disabled", "disabled");
        gfDistrict.val("default");
        gfDistrict.attr("disabled", "disabled");
    } else {
        gfCity.removeAttr("disabled");
        gfDistrict.val("default");
        gfDistrict.attr("disabled", "disabled");
        $.ajax({

        });
    }
});