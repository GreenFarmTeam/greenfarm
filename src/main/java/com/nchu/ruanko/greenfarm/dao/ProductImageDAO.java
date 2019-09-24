package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ProductImageDAO {

    /**
     *
     * @param productImage
     * @param productUid
     */
    @Insert("INSERT INTO gf_tb_product_image(img_uid,img_path,img_is_main,img_product_uid)" +
            " VALUES(#{img.productImageUid},#{img.productImagePath},#{img.productImageIsMainImage},#{uid})")
    void insertProductImageWithProductUID(@Param(value = "img") ProductImage productImage, @Param(value = "uid") String productUid);

    /**
     *
     * @param productUid
     */
    @Delete("DELETE FROM gf_tb_product_image" +
            " WHERE img_product_uid=#{uid}")
    void deleteProductImageByProductUID(@Param(value = "uid") String productUid);


    /**
     *
     * @param productUid
     * @return
     */
    @Results(id = "productImageMapper1", value = {
            @Result(property = "productImageUid", column = "img_uid"),
            @Result(property = "productImagePath", column = "img_path"),
            @Result(property = "productImageIsMainImage", column = "img_is_main"),
            @Result(property = "product", column = "img_product_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.ProductDAO.getProductByProductUID"))
    })
    @Select("SELECT *" +
            " FROM gf_tb_product_image" +
            " WHERE img_product_uid=#{uid}")
    List<ProductImage> listProductImagesByProductUID(@Param(value = "uid") String productUid);

    /**
     *
     * @param productUid
     * @return
     */
    @ResultMap(value = "productImageMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product_image" +
            " WHERE img_product_uid=#{uid}" +
            " AND img_is_main=1")
    ProductImage getProductMainImageByProductUID(@Param(value = "uid") String productUid);

    /**
     *
     * @param productUid
     * @return
     */
    @ResultMap(value = "productImageMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product_image" +
            " WHERE img_product_uid=#{uid}" +
            " AND img_is_main=0")
    List<ProductImage> listProductOtherImagesByProductUID(@Param(value = "uid") String productUid);

}