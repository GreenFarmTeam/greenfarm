package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.BusinessReview;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BusinessReviewDAO {

    @Results(id = "businessReviewMapper1", value = {
            @Result(column = "rev_uid", property = "reviewUid"),
            @Result(column = "rev_stime", property = "reviewSubmitTime"),
            @Result(column = "rev_rtime", property = "reviewTime"),
            @Result(column = "rev_result", property = "reviewResult"),
            @Result(column = "rev_reason", property = "reviewReason"),
            @Result(column = "rev_bus_uid", property = "business", one = @One(select = "com.nchu.ruanko.greenfarm.dao.BusinessDAO.getBusinessByUID"))
    })
    @Select("SELECT rev_stime,rev_rtime,rev_result,rev_reason" +
            " FROM gf_tb_business_review" +
            " WHERE rev_bus_uid=#{uid}")
    List<BusinessReview> listBusinessReviewsByBusinessUID(@Param(value = "uid") String businessUid);

    @Insert("INSERT INTO gf_tb_business_review(rev_uid,rev_bus_uid,rev_stime,rev_rtime,rev_result,rev_reason)" +
            " VALUES(#{review.reviewUid},#{uid},#{review.reviewSubmitTime},#{review.reviewTime},#{review.reviewResult},#{review.reviewReason})")
    void insertBusinessReview(@Param(value = "review") BusinessReview review, @Param(value = "uid") String businessUid);

    @Select("SELECT COUNT(*)" +
            " FROM gf_tb_business_review" +
            " WHERE rev_result IS NULL" +
            " AND rev_bus_uid=#{uid}")
    int countBusinessReviewUnfinishedByBusinessUID(@Param(value = "uid") String businessUid);

}