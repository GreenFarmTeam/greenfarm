package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Farm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FarmDAO {

    @Insert("INSERT INTO gf_tb_farm(farm_uid,farm_price,farm_unit,farm_year,farm_area,farm_desc,farm_lng,farm_lat,farm_ctime,farm_type_uid,farm_business_uid)" +
            " VALUES(#{farm.farmUid},#{farm.farmPrice},#{farm.farmUnit},#{farm.farmYear},#{farm.farmArea},#{farm.farmDescription},#{farm.farmLongitude},#{farm.farmLatitude},#{farm.farmUpDate},#{typeUid},#{uid})")
    void insertFarm(@Param(value = "farm") Farm farm, @Param(value = "typeUid") String farmTypeUid, @Param(value = "uid") String businessUid);

}