package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductType;
import org.apache.ibatis.annotations.*;
import java.util.Date;
import java.util.List;

@Mapper
public interface ProductDAO {

    /**
     *
     * @param product
     */
    @Insert("INSERT INTO gf_tb_product(prdt_uid,prdt_name,prdt_price,prdt_unit,prdt_desc,prdt_ctime,prdt_stock,prdt_is_recommend,prdt_type_uid,prdt_business_uid)" +
            " VALUES(#{pd.productUid},#{pd.productName},#{pd.productPrice},#{pd.productUnit},#{pd.productDescription},#{pd.productUpDate},#{pd.productStock},#{pd.productIsRecommend},#{pd.productType.typeUid},#{pd.business.businessUid})")
    void insertProduct(@Param(value = "pd") Product product);

    /**
     *
     * @param product
     * @param productTypeUid
     * @param businessUid
     */
    @Insert("INSERT INTO gf_tb_product(prdt_uid,prdt_name,prdt_price,prdt_unit,prdt_desc,prdt_ctime,prdt_stock,prdt_is_recommend,prdt_type_uid,prdt_business_uid)" +
            " VALUES(#{pd.productUid},#{pd.productName},#{pd.productPrice},#{pd.productUnit},#{pd.productDescription},#{pd.productUpDate},#{pd.productStock},#{pd.productIsRecommend},#{typeUid},#{businessUid})")
    void insertProductWithProductTypeUIDAndBusinessUID(@Param(value = "pd") Product product, @Param(value = "typeUid") String productTypeUid, @Param(value = "businessUid") String businessUid);


    /**
     *
     * @param date
     * @param productUid
     */
    @Update("UPDATE gf_tb_product" +
            " SET prdt_ctime=#{date}" +
            " WHERE prdt_uid=#{uid}")
    void updateProductUpDateByProductUID(@Param(value = "date") Date date, @Param(value = "uid") String productUid);

    /**
     *
     * @param isRecommend
     * @param productUid
     */
    @Update("UPDATE gf_tb_product" +
            " SET prdt_is_recommend=#{recommend}" +
            " WHERE prdt_uid=#{uid}")
    void updateProductIsRecommendByProductUID(@Param(value = "recommend") Integer isRecommend, @Param(value = "uid") String productUid);

    /**
     *
     * @param stock
     * @param productUid
     */
    @Update("UPDATE gf_tb_product" +
            " SET prdt_stock=#{stock}" +
            " WHERE prdt_uid=#{uid}")
    void updateProductStockByProductUID(@Param(value = "stock") Integer stock, @Param(value = "uid") String productUid);

    /**
     *
     * @param productUid
     */
    @Delete("DELETE FROM gf_tb_product" +
            " WHERE prdt_uid=#{uid}")
    void deleteProductByProductUID(@Param(value = "uid") String productUid);

    /**
     *
     * @param productUid
     * @return
     */
    @Results(id = "productMapper1", value = {
            @Result(property = "productUid", column = "prdt_uid"),
            @Result(property = "productName", column = "prdt_name"),
            @Result(property = "productPrice", column = "prdt_price"),
            @Result(property = "productUnit", column = "prdt_unit"),
            @Result(property = "productDescription", column = "prdt_desc"),
            @Result(property = "productUpDate", column = "prdt_ctime"),
            @Result(property = "productStock", column = "prdt_stock"),
            @Result(property = "productIsRecommend", column = "prdt_is_recommend"),
            @Result(property = "productType", column = "prdt_type_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.ProductTypeDAO.getProductTypeByUID")),
            @Result(property = "business", column = "prdt_business_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.BusinessDAO.getBusinessByUID"))
    })
    @Select("SELECT *" +
            " FROM gf_tb_product" +
            " WHERE prdt_uid=#{uid}")
    Product getProductByProductUID(@Param(value = "uid") String productUid);

    /**
     *
     * @param businessUid
     * @return
     */
    @ResultMap(value = "productMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product" +
            " WHERE prdt_business_uid=#{uid}")
    List<Product> listProductsByBusinessUID(@Param(value = "uid") String businessUid);

    /**
     *
     * @param isRecommend
     * @return
     */
    @ResultMap(value = "productMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product" +
            " WHERE prdt_is_recommend=#{recommend}")
    List<Product> listProductsByProductIsRecommend(@Param(value = "recommend") Integer isRecommend);

    /**
     * 获取未被审核的商品
     *
     * @return
     */
    @ResultMap(value = "productMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product" +
            " WHERE prdt_ctime IS NULL")
    List<Product> listUnreviewedProduct();

    /**
     * 获取审核不通过的商品的 uid
     *
     * @return
     */
    @Select("SELECT prdt_uid" +
            " FROM gf_tb_product" +
            " WHERE prdt_ctime='1000-01-01 00:00:00'")
    List<String> listNoPassReviewProductUIDs();

    /**
     * 获取当前商家 审核通过 且 上架 的商品
     *
     * @param businessUid
     * @return
     */
    @ResultMap(value = "productMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product" +
            " WHERE prdt_business_uid=#{uid}" +
            " AND prdt_ctime IS NOT NULL" +
            " AND prdt_ctime<>'1000-01-01 00:00:00'" +
            " AND prdt_ctime<>'1001-01-01 00:00:00'" +
            " AND prdt_ctime<>'1002-01-01 00:00:00'" +
            " ORDER BY prdt_ctime DESC")
    List<Product> listPassReviewAndUpProductsByBusinessUID(@Param(value = "uid") String businessUid);

    /**
     * 获取全部 审核通过 且 上架 的商品
     *
     * @return
     */
    @ResultMap(value = "productMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product" +
            " WHERE prdt_ctime IS NOT NULL" +
            " AND prdt_ctime<>'1000-01-01 00:00:00'" +
            " AND prdt_ctime<>'1001-01-01 00:00:00'" +
            " AND prdt_ctime<>'1002-01-01 00:00:00'" +
            " ORDER BY prdt_business_uid ASC")
    List<Product> listPassReviewAndUpProduct();

    /**
     * 获取全部 通过审核 且 下架商品
     *
     * @return
     */
    @ResultMap(value = "productMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product" +
            " WHERE prdt_ctime='1001-01-01 00:00:00'" +
            " OR prdt_ctime='1002-01-01 00:00:00'" +
            " ORDER BY prdt_business_uid ASC")
    List<Product> listPassReviewAndDownProducts();

    /**
     * 获取当前商家下架的商品
     *
     * @param businessUid
     * @return
     */
    @ResultMap(value = "productMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product" +
            " WHERE prdt_business_uid=#{uid}" +
            " AND (prdt_ctime='1001-01-01 00:00:00'" +
            " OR prdt_ctime='1002-01-01 00:00:00')")
    List<Product> listPassReviewAndDownProductsByBusinessUID(@Param(value = "uid") String businessUid);

    @ResultMap(value = "productMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product,gf_tb_product_type" +
            " WHERE type_uid=#{uid} and gf_tb_product_type.type_uid = gf_tb_product.prdt_type_uid")
    List<Product> selectAllProdcutsByClassificationId(@Param("uid") String classificationId);

    @ResultMap(value = "productMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product" +
            " WHERE prdt_is_recommend=1")
    List<Product> listAllRecommendProducts();

    @ResultMap(value = "productMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product" +
            " WHERE prdt_is_recommend=0")
    List<Product> listAllNotRecommendProducts();
}