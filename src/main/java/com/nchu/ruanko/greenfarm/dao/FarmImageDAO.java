package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.FarmImage;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface FarmImageDAO {

    @Insert("INSERT INTO gf_tb_farm_image(img_uid,img_path,img_is_main,img_farm_uid)" +
            " VALUES(#{img.farmImageUid},#{img.farmImagePath},#{img.farmImageIsMainImage},#{uid})")
    void insertFarmImage(@Param(value = "img") FarmImage farmImage, @Param(value = "uid") String farmUid);

    @Results(id = "farmImageMapper1", value = {
            @Result(property = "farmImageUid", column = "img_uid"),
            @Result(property = "farmImagePath", column = "img_path"),
            @Result(property = "farmImageIsMainImage", column = "img_is_main"),
            @Result(property = "farm", column = "img_farm_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.FarmDAO.getFarmByFarmUID"))
    })
    @Select("SELECT *" +
            " FROM gf_tb_farm_image" +
            " WHERE img_farm_uid=#{uid}" +
            " AND img_is_main=1")
    FarmImage getFarmMainImageByFarmUID(@Param(value = "uid") String farmUid);

    @ResultMap(value = "farmImageMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_farm_image" +
            " WHERE img_farm_uid=#{uid}" +
            " AND img_is_main=0")
    List<FarmImage> listFarmOtherImagesByFarmUID(@Param(value = "uid") String farmUid);


    @Select("select *"+
           "from gf_tb_farm_image "+"" +
            " WHERE img_farm_uid=#{uid}" +
            " AND img_is_main=1")
    List<FarmImage> getFarmOtherImageByFarmUID(String farmUid);
}