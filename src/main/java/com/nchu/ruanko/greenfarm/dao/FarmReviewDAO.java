package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.FarmReview;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FarmReviewDAO {

    @Insert("INSERT INTO gf_tb_farm_review(rev_uid,rev_farm_uid,rev_stime,rev_rtime,rev_result,rev_reason)" +
            " VALUES(#{review.farmReviewUid},#{uid},#{review.farmReviewSubmitTime},#{review.farmReviewReviewTime},#{review.farmReviewResult},#{review.farmReviewReason})")
    void insertFarmReview(@Param(value = "review") FarmReview farmReview, @Param(value = "uid") String farmUid);

}