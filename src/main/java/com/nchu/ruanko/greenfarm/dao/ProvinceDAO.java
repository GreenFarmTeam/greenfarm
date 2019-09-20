package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ProvinceDAO {

    @Results(id = "provinceMapper1", value = {
            @Result(property = "provinceId", column = "province_id"),
            @Result(property = "provinceName", column = "province_name")
    })
    @Select("SELECT *" +
            " FROM gf_tb_province" +
            " ORDER BY province_id")
    List<Province> listAllProvinces();

}
