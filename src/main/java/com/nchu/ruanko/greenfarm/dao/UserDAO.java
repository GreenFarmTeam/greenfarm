package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
            @Result(property = "userNickname", column = "user_nickname"),
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
     * @param phone
     * @return
     */
    @ResultMap(value = "userMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_user" +
            " WHERE user_phone=#{phone}")
    User getUserByPhone(@Param(value = "phone") String phone);


    /**
     *
     * @param idcard
     * @param realname
     * @return
     */
    @ResultMap(value = "userMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_user" +
            " WHERE user_idcard=#{idcard}" +
            " AND user_realname=#{realname}")
    User getUserByIdcardAndRealname(@Param(value = "idcard") String idcard, @Param(value = "realname") String realname);


    /**
     *
     * @param mail
     * @return
     */
    @ResultMap(value = "userMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_user" +
            " WHERE user_mail=#{mail}")
    User getUserByMail(@Param(value = "mail") String mail);

    /**
     *
     * @param username
     * @return
     */
    @ResultMap(value = "userMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_user" +
            " WHERE user_username=#{username}")
    User getUserByUsername(@Param(value = "username") String username);

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
     * @param idcard
     * @return
     */
    @Select("SELECT COUNT(*)" +
            " FROM gf_tb_user" +
            " WHERE user_idcard=#{idcard}")
    int countUserByIdcard(@Param(value = "idcard") String idcard);

    /**
     *
     * @param user
     */
    @Insert("INSERT INTO gf_tb_user" +
            " VALUES(#{userUid},#{userUsername},#{userPassword},#{userNickname},#{userPhone},#{userMail},#{userIdcard},#{userRealname},#{userPhoto},#{userTime},#{userIsBusiness})")
    void insertUser(User user);

    /**
     *
     * @param nickname
     * @param uid
     */
    @Update("UPDATE gf_tb_user" +
            " SET user_nickname=#{nickname}" +
            " WHERE user_uid=#{uid}")
    void updateUserNicknameByUID(@Param(value = "nickname") String nickname, @Param(value = "uid") String uid);

    /**
     *
     * @param realname
     * @param idcard
     * @param uid
     */
    @Update("UPDATE gf_tb_user" +
            " SET user_realname=#{realname},user_idcard=#{idcard}" +
            " WHERE user_uid=#{uid}")
    void updateUserRealnameAndIdcardByUID(@Param(value = "realname") String realname, @Param(value = "idcard") String idcard, @Param(value = "uid") String uid);

    /**
     *
     * @param mail
     * @param uid
     */
    @Update("UPDATE gf_tb_user" +
            " SET user_mail=#{mail}" +
            " WHERE user_uid=#{uid}")
    void updateUserMailByUID(@Param(value = "mail") String mail, @Param(value = "uid") String uid);

    /**
     *
     * @param phone
     * @param uid
     */
    @Update("UPDATE gf_tb_user" +
            " SET user_phone=#{phone}" +
            " WHERE user_uid=#{uid}")
    void updateUserPhoneByUID(@Param(value = "phone") String phone, @Param(value = "uid") String uid);

    /**
     *
     * @param password
     * @param uid
     */
    @Update("UPDATE gf_tb_user" +
            " SET user_password=#{password}" +
            " WHERE user_uid=#{uid}")
    void updateUserPasswordByUID(@Param(value = "password") String password, @Param(value = "uid") String uid);

    /**
     *
     * @param isBusiness
     * @param uid
     */
    @Update("UPDATE gf_tb_user" +
            " SET user_is_business=#{isBusiness}" +
            " WHERE user_uid=#{uid}")
    void updateUserIsBusinessByUID(@Param(value = "isBusiness") Integer isBusiness, @Param(value = "uid") String uid);

    @ResultMap(value = "userMapper1")
    @Select("select * from gf_tb_user " +
            "where user_state=1 ")
    List<User> listAllMembers();

    @Update("UPDATE gf_tb_user" +
            " SET user_nickname=#{nickname}" +
            " WHERE user_uid in" +
            "(" +
            " select bus_user_uid " +
            " from gf_tb_business " +
            " where bus_uid = #{businessUID} " +
            ")")
    void updateUserNicknameByBusinessUID(@Param("nickname") String nickName,@Param("businessUID") String businessUID);

    @Update("UPDATE gf_tb_user" +
            " SET user_state=#{status}" +
            " WHERE user_uid = #{id}")
    int updateUserDateByUserId(@Param("id") String memberID,@Param("status") int status);

    @ResultMap(value = "userMapper1")
    @Select("select * from gf_tb_user " +
            "where user_state=0 ")
    List<User> listAllIllegalMembers();
}
