package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.ProductType;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 对应数据库表 gf_tb_product_type
 *
 * @author Yuan Yueshun
 */
@Mapper
public interface ProductTypeDAO {

    /**
     *
     * @return List
     */
    @Results(id = "productTypeMapper1", value = {
            @Result(property = "typeUid", column = "type_uid"),
            @Result(property = "typeName", column = "type_name"),
            @Result(property = "typeDesc", column = "type_desc")
    })
    @Select("SELECT *" +
            " FROM gf_tb_product_type")
    List<ProductType> listAllProductTypes();

    /**
     *
     * @param uid UID
     * @return ProductType
     */
    @ResultMap(value = "productTypeMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_product_type" +
            " WHERE type_uid=#{uid}")
    ProductType getProductTypeByUID(@Param(value = "uid") String uid);

}