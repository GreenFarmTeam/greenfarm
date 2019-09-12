package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * 对应数据库的表 gf_tb_user
 *
 * @author Yuan Yueshun
 */
@Mapper
public interface UserDAO {

    /**
     *
     * @param userName
     * @param password
     * @return
     */
    @Results(id = "userMapper1", value = {
            @Result(property = "userUid", column = "user_uid"),
            @Result(property = "userUsername", column = "user_username"),
            @Result(property = "userPassword", column = "user_password"),
            @Result(property = "userNickName", column = "user_nickname"),
            @Result(property = "userPhone", column = "user_phone"),
            @Result(property = "userMail", column = "user_mail"),
            @Result(property = "userIdcard", column = "user_idcard"),
            @Result(property = "userRealname", column = "user_realname"),
            @Result(property = "userPhoto", column = "user_photo"),
            @Result(property = "userTime", column = "user_time"),
            @Result(property = "userIsBusiness", column = "user_is_business")
    })
    @Select("SELECT *" +
            " FROM gf_tb_user" +
            " WHERE user_username=#{username} AND user_password=#{password}")
    User getUserByUsernameAndPassword(@Param(value = "username") String userName, @Param(value = "password") String password);


    /**
     *
     * @param phone
     * @param password
     * @return
     */
    @ResultMap(value = "userMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_user" +
            " WHERE user_phone=#{phone} AND user_password=#{password}")
    User getUserByPhoneAndPassword(@Param(value = "phone") String phone, @Param(value = "password") String password);

    /**
     *
     * @param mail
     * @param password
     * @return
     */
    @ResultMap(value = "userMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_user" +
            " WHERE user_mail=#{mail} AND user_password=#{password}")
    User getUserByMailAndPassword(@Param(value = "mail") String mail, @Param(value = "password") String password);

    /**
     *
     * @param uid
     * @return
     */
    @ResultMap(value = "userMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_user" +
            " WHERE user_uid=#{uid}")
    User getUserByUID(@Param(value = "uid") String uid);

    /**
     *
     * @param mail
     * @return
     */
    @Select("SELECT COUNT(*)" +
            " FROM gf_tb_user" +
            " WHERE user_mail=#{mail}")
    int countUserByMail(@Param(value = "mail") String mail);

    /**
     *
     * @param phone
     * @return
     */
    @Select("SELECT COUNT(*)" +
            " FROM gf_tb_user" +
            " WHERE user_phone=#{phone}")
    int countUserByPhone(@Param(value = "phone") String phone);

    /**
     *
     * @param user
     */
    @Insert("INSERT INTO gf_tb_user" +
            " VALUES(#{userUid},#{userUsername},#{userPassword},#{userNickName},#{userPhone},#{userMail},#{userIdcard},#{userRealname},#{userPhoto},#{userTime},#{userIsBusiness})")
    void insertUser(User user);

}
