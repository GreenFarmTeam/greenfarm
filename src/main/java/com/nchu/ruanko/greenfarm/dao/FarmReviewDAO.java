package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.FarmReview;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface FarmReviewDAO {

    @Insert("INSERT INTO gf_tb_farm_review(rev_uid,rev_farm_uid,rev_stime,rev_rtime,rev_result,rev_reason)" +
            " VALUES(#{review.farmReviewUid},#{uid},#{review.farmReviewSubmitTime},#{review.farmReviewReviewTime},#{review.farmReviewResult},#{review.farmReviewReason})")
    void insertFarmReview(@Param(value = "review") FarmReview farmReview, @Param(value = "uid") String farmUid);

    @Results(id = "farmReviewMapper", value = {
            @Result(property = "farmReviewUid", column = "rev_uid"),
            @Result(property = "farmReviewSubmitTime", column = "rev_stime"),
            @Result(property = "farmReviewReviewTime", column = "rev_rtime"),
            @Result(property = "farmReviewResult", column = "rev_result"),
            @Result(property = "farmReviewReason", column = "rev_reason"),
            @Result(property = "farm", column = "rev_farm_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.FarmDAO.getFarmByFarmUID"))
    })
    @Select("SELECT *" +
            " FROM gf_tb_farm_review" +
            " WHERE rev_farm_uid=#{uid}")
    FarmReview getFarmReviewByFarmUID(@Param(value = "uid") String farmUid);

    @Select("SELECT rev_farm_uid" +
            " FROM gf_tb_farm_review" +
            " WHERE rev_uid=#{farmReviewUid}")
    String getFarmIdByFarmReviewId(String farmReviewUid);

    @Update("update gf_tb_farm_review "+
            "set rev_rtime=#{date}" +
            " WHERE rev_uid=#{uid}")
    void updateFarmReviewReviewTimeByFarmReviewUID(@Param(value = "date")Date date,@Param(value = "uid") String farmReviewUid);
    @Update(" update gf_tb_farm_review "+
            " set rev_result=#{result},rev_reason=#{reason} " +
            " WHERE rev_uid=#{uid}")
    void updateFarmReviewResultAndFarmReviewReasonByFarmReviewUID(@Param(value = "result")int i,@Param(value = "reason") Object o,@Param(value = "uid") String farmReviewUid);
}