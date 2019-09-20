package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessReview;
import org.apache.ibatis.annotations.*;
import java.util.Date;
import java.util.List;

@Mapper
public interface BusinessReviewDAO {

    /**
     *
     * @param businessUid
     * @return
     */
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
            " WHERE rev_bus_uid=#{uid}" +
            " ORDER BY rev_stime ASC")
    List<BusinessReview> listBusinessReviewsByBusinessUID(@Param(value = "uid") String businessUid);

    /**
     *
     * @return
     */
    @ResultMap(value = "businessReviewMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_business_review" +
            " WHERE rev_result IS NULL" +
            " ORDER BY rev_stime ASC")
    List<BusinessReview> listUnfinishedBusinessReviews();

    /**
     *
     * @param reviewUid
     * @return
     */
    @ResultMap(value = "businessReviewMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_business_review" +
            " WHERE rev_uid=#{uid}")
    BusinessReview getBusinessReviewByReviewUID(@Param(value = "uid") String reviewUid);

    /**
     *
     * @param review
     * @param businessUid
     */
    @Insert("INSERT INTO gf_tb_business_review(rev_uid,rev_bus_uid,rev_stime,rev_rtime,rev_result,rev_reason)" +
            " VALUES(#{review.reviewUid},#{uid},#{review.reviewSubmitTime},#{review.reviewTime},#{review.reviewResult},#{review.reviewReason})")
    void insertBusinessReview(@Param(value = "review") BusinessReview review, @Param(value = "uid") String businessUid);

    /**
     *
     * @param businessUid
     * @return
     */
    @Select("SELECT COUNT(*)" +
            " FROM gf_tb_business_review" +
            " WHERE rev_result IS NULL" +
            " AND rev_bus_uid=#{uid}")
    int countBusinessReviewUnfinishedByBusinessUID(@Param(value = "uid") String businessUid);

    /**
     *
     * @param date
     * @param reviewUid
     */
    @Update("UPDATE gf_tb_business_review" +
            " SET rev_rtime=#{date}" +
            " WHERE rev_uid=#{uid}")
    void updateReviewTimeByReviewUID(@Param(value = "date") Date date, @Param(value = "uid") String reviewUid);

    /**
     *
     * @param result
     * @param reviewUid
     */
    @Update("UPDATE gf_tb_business_review" +
            " SET rev_result=#{result}" +
            " WHERE rev_uid=#{uid}")
    void updateReviewResultByReviewUID(@Param(value = "result") Integer result, @Param(value = "uid") String reviewUid);

    /**
     *
     * @param reason
     * @param reviewUid
     */
    @Update("UPDATE gf_tb_business_review" +
            " SET rev_reason=#{reason}" +
            " WHERE rev_uid=#{uid}")
    void updateReviewReasonByReviewUID(@Param(value = "reason") String reason, @Param(value = "uid") String reviewUid);



}