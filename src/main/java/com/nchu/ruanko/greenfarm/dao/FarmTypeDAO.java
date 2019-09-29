package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.FarmType;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface FarmTypeDAO {

    @Results(id = "farmTypeMapper1", value = {
            @Result(property = "farmTypeUid", column = "flt_uid"),
            @Result(property = "farmTypeName", column = "flt_name")
    })
    @Select("SELECT *" +
            " FROM gf_tb_farm_land_type")
    List<FarmType> listFarmTypes();

    @ResultMap(value = "farmTypeMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_farm_land_type" +
            " WHERE flt_uid=#{uid}")
    FarmType getFarmTypeByFarmTypeUID(@Param(value = "uid") String farmTypeUid);

}