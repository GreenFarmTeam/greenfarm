package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Administrator;
import org.apache.ibatis.annotations.*;

/**
 * 对应数据库表 gf_tb_administrator
 *
 * @author Yuan Yueshun
 */
@Mapper
public interface AdministratorDAO {

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @Results(id = "administratorMapper1", value = {
            @Result(property = "adminUsername", column = "admin_username"),
            @Result(property = "adminPassword", column = "admin_password"),
            @Result(property = "adminPhone", column = "admin_phone"),
            @Result(property = "adminMail", column = "admin_mail")
    })
    @Select("SELECT *" +
            " FROM gf_tb_administrator" +
            " WHERE admin_username=#{username}" +
            " AND admin_password=#{password}")
    Administrator getAdministratorByUsernameAndPassword(@Param(value = "username") String username, @Param(value = "password") String password);

}