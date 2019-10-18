package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.FarmReviewImage;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductReviewImage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductReviewImageDAO {
    @Results(id = "productImageMapper1", value = {
            @Result(property = "productReviewImageUid", column = "product_review_image_uid"),
            @Result(property = "productReviewImagePath", column = "product_review_image_path"),
            @Result(property = "productReviewImageOrder", column = "product_review_image_order_uid",
                    one = @One(select = "com.nchu.ruanko.greenfarm.dao.OrderDAO.loadOneOrderByOrderID"))
    })
    @Select("select * from gf_tb_product_review_image")
    public List<ProductReviewImage> selectAllProductReviewImage();

    @Insert("insert into gf_tb_product_review_image values(#{productReviewImageUid},#{productReviewImagePath},#{orderId})")
    void insertProductReviewImage(@Param("productReviewImageUid") String productReviewImageUid,@Param("orderId") String orderId,@Param("productReviewImagePath") String productReviewImagePath);


    @ResultMap(value="productImageMapper1")
    @Select(" select * from " +
            " gf_tb_product_review_image " +
            " where product_review_image_order_uid in ( " +
            " select item_order_uid from " +
            " gf_tb_order_item " +
            " where item_product_uid = #{productId} " +
            " " +
            " )")
    List<ProductReviewImage> selectAllProductReviewImageByProductId(@Param("productId") String productId);
}
