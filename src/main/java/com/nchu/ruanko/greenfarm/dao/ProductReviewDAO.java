package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.ProductReview;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductReviewDAO {

    /**
     *
     * @param productReview
     * @param productUid
     */
    @Insert("INSERT INTO gf_tb_product_review(rev_uid,rev_stime,rev_rtime,rev_result,rev_reason,rev_product_uid)" +
            " VALUES(#{review.productReviewUid},#{review.productReviewSubmitTime},#{review.productReviewReviewTime},#{review.productReviewResult},#{review.productReviewReason},#{uid})")
    void insertProductReviewWithProductUID(@Param(value = "review") ProductReview productReview, @Param(value = "uid") String productUid);


    @Results(id = "productReviewMapper1", value = {
            @Result(property = "productReviewUid", column = "rev_uid"),
            @Result(property = "productReviewSubmitTime", column = "rev_stime"),
            @Result(property = "productReviewReviewTime", column = "rev_rtime"),
            @Result(property = "productReviewResult", column = "rev_result"),
            @Result(property = "productReviewReason", column = "rev_reason"),
            @Result(property = "product", column = "rev_product_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.ProductDAO.getProductByProductUID"))
    })
    @Select("SELECT *" +
            " FROM gf_tb_product_review" +
            " WHERE rev_product_uid=#{uid}")
    ProductReview getProductReviewByProductUID(@Param(value = "uid") String productUid);

}