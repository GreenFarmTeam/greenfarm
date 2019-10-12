package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Address;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AddressDAO {

    /**
     *
     * @param userUid
     * @return
     */
    @Results(id = "addressMapper1", value = {
            @Result(property = "addressUid", column = "addr_uid"),
            @Result(property = "addressName", column = "addr_name"),
            @Result(property = "addressProvince", column = "addr_province"),
            @Result(property = "addressCity", column = "addr_city"),
            @Result(property = "addressDistrict", column = "addr_district"),
            @Result(property = "addressDetail", column = "addr_desc"),
            @Result(property = "addressPhone", column = "addr_phone"),
            @Result(property = "user", column = "addr_user_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.UserDAO.getUserByUID"))
    })
    @Select("SELECT *" +
            " FROM gf_tb_address" +
            " WHERE addr_user_uid=#{uid}")
    List<Address> listAddressesByUserUID(@Param(value = "uid") String userUid);

    /**
     *
     * @param addressUid
     * @return
     */
    @ResultMap(value = "addressMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_address" +
            " WHERE addr_uid=#{uid}")
    Address getAddressByAddressUID(@Param(value = "uid") String addressUid);

    /**
     *
     * @param address
     * @param userUid
     */
    @Insert("INSERT INTO gf_tb_address(addr_uid,addr_name,addr_province,addr_city,addr_district,addr_desc,addr_user_uid)" +
            " VALUES(#{addr.addressUid},#{addr.addressName},#{addr.addressProvince},#{addr.addressCity},#{addr.addressDistrict},#{addr.addressDetail},#{uid})")
    void insertAddress(@Param(value = "addr") Address address, @Param("uid") String userUid);

    /**
     *
     * @param address
     * @param addressUid
     */
    @Update("UPDATE gf_tb_address" +
            " SET addr_name=#{addr.addressName},addr_province=#{addr.addressProvince},addr_city=#{addr.addressCity},addr_district=#{addr.addressDistrict},addr_desc=#{addr.addressDetail}" +
            " WHERE addr_uid=#{uid}")
    void updateAddressByAddressUID(@Param(value = "addr") Address address, @Param(value = "uid") String addressUid);

    /**
     *
     * @param addressUid
     */
    @Delete("DELETE FROM gf_tb_address" +
            " WHERE addr_uid=#{uid}")
    void deleteAddressByAddressUID(@Param(value = "uid") String addressUid);

}
