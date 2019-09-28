package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.FarmImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FarmImageDAO {

    @Insert("INSERT INTO gf_tb_farm_image(img_uid,img_path,img_is_main,img_farm_uid)" +
            " VALUES(#{img.farmImageUid},#{img.farmImagePath},#{img.farmImageIsMainImage},#{uid})")
    void insertFarmImage(@Param(value = "img") FarmImage farmImage, @Param(value = "uid") String farmUid);

}