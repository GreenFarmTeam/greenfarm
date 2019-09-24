package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.ProductReview;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

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

    /**
     *
     * @param date
     * @param productReviewUid
     */
    @Update("UPDATE gf_tb_product_review" +
            " SET rev_rtime=#{date}" +
            " WHERE rev_uid=#{uid}")
    void updateProductReviewReviewTimeByProductReviewUID(@Param(value = "date") Date date, @Param(value = "uid") String productReviewUid);

    /**
     *
     * @param productReviewResult
     * @param productReviewReason
     * @param productReviewUid
     */
    @Update("UPDATE gf_tb_product_review" +
            " SET rev_result=#{result},rev_reason=#{reason}" +
            " WHERE rev_uid=#{uid}")
    void updateProductReviewResultAndProductReviewReasonByProductReviewUID(@Param(value = "result") Integer productReviewResult, @Param(value = "reason") String productReviewReason, @Param(value = "uid") String productReviewUid);

    /**
     *
     * @param productUid
     */
    @Delete("DELETE FROM gf_tb_product_review" +
            " WHERE rev_product_uid=#{uid}")
    void deleteProductReviewByProductUID(@Param(value = "uid") String productUid);


   /**
     *
     * @param productUid
     * @return
     */
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

    /**
     *
     * @return
     */
    @ResultMap(value = "productReviewMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product_review" +
            " WHERE rev_result IS NULL" +
            " ORDER BY rev_stime DESC")
    List<ProductReview> listUnreviewedProductReviewsOrderBySubmitTimeDesc();

    /**
     *
     * @param productReviewUid
     * @return
     */
    @Select("SELECT rev_product_uid" +
            " FROM gf_tb_product_review" +
            " WHERE rev_uid=#{uid}")
    String getProductUIDByProductReviewUID(@Param(value = "uid") String productReviewUid);

}