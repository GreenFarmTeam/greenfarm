package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Farm;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface FarmDAO {

    @Insert("INSERT INTO gf_tb_farm(farm_uid,farm_price,farm_unit,farm_year,farm_area,farm_desc,farm_lng,farm_lat,farm_ctime,farm_type_uid,farm_business_uid)" +
            " VALUES(#{farm.farmUid},#{farm.farmPrice},#{farm.farmUnit},#{farm.farmYear},#{farm.farmArea},#{farm.farmDescription},#{farm.farmLongitude},#{farm.farmLatitude},#{farm.farmUpDate},#{typeUid},#{uid})")
    void insertFarm(@Param(value = "farm") Farm farm, @Param(value = "typeUid") String farmTypeUid, @Param(value = "uid") String businessUid);


    @Results(id = "farmMapper1", value = {
            @Result(property = "farmUid", column = "farm_uid"),
            @Result(property = "farmPrice", column = "farm_price"),
            @Result(property = "farmUnit", column = "farm_unit"),
            @Result(property = "farmYear", column = "farm_year"),
            @Result(property = "farmArea", column = "farm_area"),
            @Result(property = "farmDescription", column = "farm_desc"),
            @Result(property = "farmLongitude", column = "farm_lng"),
            @Result(property = "farmLatitude", column = "farm_lat"),
            @Result(property = "farmUpDate", column = "farm_ctime"),
            @Result(property = "business", column = "farm_business_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.BusinessDAO.getBusinessByUID")),
            @Result(property = "type", column = "farm_type_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.FarmTypeDAO.getFarmTypeByFarmTypeUID"))
    })
    @Select("SELECT *" +
            " FROM gf_tb_farm" +
            " WHERE farm_business_uid=#{uid}")
    List<Farm> listFarmsByBusinessUID(@Param(value = "uid") String businessUid);

    @ResultMap(value = "farmMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_farm" +
            " WHERE farm_uid=#{uid}")
    Farm getFarmByFarmUID(@Param(value = "uid") String farmUid);
}