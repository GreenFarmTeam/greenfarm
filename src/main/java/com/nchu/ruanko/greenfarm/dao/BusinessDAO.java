package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 *
 FieldTypeComment
 rev_uidchar(32) NOT NULLUUID
 rev_bus_uidchar(32) NOT NULL审核商家UUID
 rev_stimedatetime NOT NULL提交时间
 rev_rtimedatetime NULL审核时间
 rev_resulttinyint(3) unsigned NULL审核结果
 rev_reasontinytext NULL未审核通过原因
 */
@Mapper
public interface BusinessDAO {

    /**
     *插入商家记录
     * @param business
     * @param userUid
     */
    @Insert("INSERT INTO gf_tb_business(bus_uid,bus_user_uid,bus_idcard_front,bus_idcard_behind,bus_shop_name,bus_shop_desc,bus_shop_state)" +
            " VALUES(#{business.businessUid},#{uid},#{business.businessIdcardFront},#{business.businessIdcardBehind},#{business.businessShopName},#{business.businessShopDescription},#{business.businessShopState})")
    void insertBusiness(@Param(value = "business") Business business, @Param(value = "uid") String userUid);

    /**
     *更新商家信息
     * @param business
     * @param businessUid
     */
    @Update("UPDATE gf_tb_business" +
            " SET bus_idcard_front=#{business.businessIdcardFront},bus_idcard_behind=#{business.businessIdcardBehind},bus_shop_name=#{business.businessShopName},bus_shop_desc=#{business.businessShopDescription},bus_shop_state=#{business.businessShopState}" +
            " WHERE bus_uid=#{uid}")
    void updateBusinessByBusinessUID(@Param(value = "business") Business business, @Param(value = "uid") String businessUid);

    /**
     *
     * @param state
     * @param businessUid
     */
    @Update("UPDATE gf_tb_business" +
            " SET bus_shop_state=#{state}" +
            " WHERE bus_uid=#{uid}")
    void updateBusinessShopStateByBusinessUID(@Param(value = "state") Integer state, @Param(value = "uid") String businessUid);

    /**
     *
     * @param uid
     * @return
     */
    @Results(id = "businessMapper1", value = {
            @Result(property = "businessUid", column = "bus_uid"),
            @Result(property = "user", column = "bus_user_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.UserDAO.getUserByUID")),
            @Result(property = "businessIdcardFront", column = "bus_idcard_front"),
            @Result(property = "businessIdcardBehind", column = "bus_idcard_behind"),
            @Result(property = "businessShopName", column = "bus_shop_name"),
            @Result(property = "businessShopDescription", column = "bus_shop_desc"),
            @Result(property = "businessShopState", column = "bus_shop_state")
    })
    /**
     * 查询某个商家根据商家ID
     */
    @Select("SELECT *" +
            " FROM gf_tb_business" +
            " WHERE bus_uid=#{uid}")
    Business getBusinessByUID(@Param(value = "uid") String uid);

    /**
     * 查询所有合法的商家
     * @return
     */
    @ResultMap(value = "businessMapper1")
    @Select("select * from gf_tb_business,gf_tb_business_review " +
            "where rev_result=1 and gf_tb_business.bus_uid=rev_bus_uid")
    List<Business>  listAllLegalBusiness();



    /**
     *
     * @param userUid
     * @return
     */
    @ResultMap(value = "businessMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_business" +
            " WHERE bus_user_uid=#{uid}")
    Business getBusinessByUserUID(@Param(value = "uid") String userUid);

    /**
     *
     * @param userUid
     * @return
     */
    @Select("SELECT bus_uid" +
            " FROM gf_tb_business" +
            " WHERE bus_user_uid=#{uid}")
    String getBusinessUIDByUserUID(@Param(value = "uid") String userUid);

    /**
     *
     * @param userUid
     * @return
     */
    @Select("SELECT COUNT(*)" +
            " FROM gf_tb_business" +
            " WHERE bus_user_uid=#{uid}")
    int countBusinessByUserUID(@Param(value = "uid") String userUid);

}