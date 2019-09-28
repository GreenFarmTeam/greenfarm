package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.FarmType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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

}