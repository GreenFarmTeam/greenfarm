package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.BusinessScope;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BusinessScopeDAO {


    /**
     *
     * @param businessUid
     * @return
     */
    @Results(id = "businessScopeMapper1", value = {
            @Result(property = "businessScopeUid", column = "scope_uid"),
            @Result(property = "business", column = "scope_business_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.BusinessDAO.getBusinessByUID")),
            @Result(property = "productType", column = "scope_type_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.ProductTypeDAO.getProductTypeByUID"))
    })
    @Select("SELECT scope_uid,scope_type_uid" +
            " FROM gf_tb_business_scope" +
            " WHERE scope_business_uid=#{uid}")
    List<BusinessScope> listBusinessScopesByBusinessUID(@Param(value = "uid") String businessUid);

    /**
     *
     * @param uid
     * @param businessUid
     * @param typeUid
     */
    @Insert("INSERT INTO gf_tb_business_scope(scope_uid,scope_business_uid,scope_type_uid)" +
            " VALUES(#{uid},#{businessUid},#{typeUid})")
    void insertBusinessScope(@Param(value = "uid") String uid, @Param(value = "businessUid") String businessUid, @Param("typeUid") String typeUid);

    /**
     *
     * @param businessUid
     */
    @Delete("DELETE FROM gf_tb_business_scope" +
            " WHERE scope_business_uid=#{uid}")
    void deleteBusinessScopeByBusinessUID(@Param(value = "uid") String businessUid);

}
