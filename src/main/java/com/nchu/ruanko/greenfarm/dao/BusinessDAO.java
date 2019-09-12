package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import org.apache.ibatis.annotations.*;

/**
 * 对应数据库的表 gf_tb_business
 *
 * @author Yuan Yueshun
 */
@Mapper
public interface BusinessDAO {

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @Results(id = "businessMapper1", value = {
            @Result(property = "businessUid", column = "business_uid"),
            @Result(property = "user", column = "business_user", one = @One(select = "com.nchu.ruanko.greenfarm.dao.UserDAO.getUserByUID")),
            @Result(property = "businessIdcardFront", column = "business_idcard_front"),
            @Result(property = "businessIdcardBehind", column = "business_idcard_behind"),
            @Result(property = "businessShopName", column = "business_shop_name"),
            @Result(property = "businessShopDescription", column = "business_shop_description"),
            @Result(property = "businessShopState", column = "business_shop_state")
    })
    @Select("SELECT *" +
            " FROM gf_tb_business,gf_tb_user" +
            " WHERE gf_tb_business.business_user=gf_tb_user.user_uid" +
            " AND gf_tb_user.user_username=#{username}" +
            " AND gf_tb_user.user_password=#{password}")
    Business getBusinessByUsernameAndPassword(@Param(value = "username") String username, @Param(value = "password") String password);

    /**
     *
     * @param business
     */
    @Insert("")
    void insertBusiness(Business business);
}
