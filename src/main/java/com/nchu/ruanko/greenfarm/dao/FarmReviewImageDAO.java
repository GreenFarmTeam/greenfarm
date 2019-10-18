package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.FarmReviewImage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FarmReviewImageDAO {
    @Results(id = "farmImageMapper1", value = {
            @Result(property = "farmReviewImageUid", column = "farm_review_image_uid"),
            @Result(property = "farmReviewImagePath", column = "farm_review_image_path"),
            @Result(property = "farmReviewImageOrderFarm", column = "farm_review_image_orderFarm_uid",
                    one = @One(select = "com.nchu.ruanko.greenfarm.dao.OrderFarmDAO.selectOneOrderFarmInfoById"))
    })
    @Select("select * from gf_tb_farm_review_image")
    public List<FarmReviewImage> selectAllFarmReviewImage();

}
