package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.OrderFarm;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface OrderFarmDAO {
    @Results(id = "OrderFarmMapper1", value = {
            @Result(property = "orderFarmUid", column = "order_farm_uid"),
            @Result(property = "orderFarmTime", column = "order_farm_time"),
            @Result(property = "orderFarmPhone", column = "order_farm_phone"),
            @Result(property = "orderFarmName", column = "order_farm_name"),
            @Result(property = "orderFarmRemark", column = "order_farm_remark"),
            @Result(property = "orderFarmState", column = "order_farm_state"),
            @Result(property = "orderFarmFarm", column = "order_farm_farm", one = @One(select = "com.nchu.ruanko.greenfarm.dao.FarmDAO.getFarmByFarmUID")),
            @Result(property = "orderFarmUser", column = "order_farm_user_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.UserDAO.getUserByUID"))
    })
    @Select("select * from gf_tb_order_farm")
    List<OrderFarm> listAllOrderFarms();


    @Insert("insert into gf_tb_order_farm values(#{order_farm_uid},#{order_farm_user_uid},#{order_farm_farm},#{order_farm_time},#{order_farm_phone},#{order_farm_name},#{order_farm_remark},#{state})")
    int insertOrderFarmInfo(@Param("order_farm_uid") String orderFarmUId,@Param("order_farm_user_uid") String userUid,@Param("order_farm_farm") String farmId,@Param("order_farm_name") String name, @Param("order_farm_phone")String phone,@Param("order_farm_time") String orderDate,@Param("order_farm_remark") String requireInfo,@Param("state") int un_pay_state);

    @ResultMap(value="OrderFarmMapper1")
    @Select("select * from gf_tb_order_farm " +
            "where order_farm_user_uid = #{userUid}")
    List<OrderFarm> selectAllOrderFarmsByUserId(@Param("userUid") String userUid);

    @ResultMap(value="OrderFarmMapper1")
    @Select("select * from gf_tb_order_farm " +
            "where order_farm_uid = #{orderFarmId}")
    OrderFarm selectOneOrderFarmInfoById(@Param("orderFarmId") String orderFarmId);

    @Update("update gf_tb_order_farm " +
            "set order_farm_state=#{state} " +
            "where order_farm_uid = #{orderFarmId}")
    void updateOrderFarmStateByOrderFarmId(@Param("orderFarmId") String orderFarmId, @Param("state") int state);

    @ResultMap(value="OrderFarmMapper1")
    @Select("select * from gf_tb_order_farm " +
            "where order_farm_farm in(" +
            " select farm_uid from" +
            " gf_tb_farm " +
            " where farm_business_uid in( " +
            " select bus_uid from" +
            " gf_tb_business,gf_tb_user " +
            " where bus_user_uid = user_uid and user_uid = #{userUid}" +
            " ) " +
            ") ")
    List<OrderFarm> loadAllOrderFarmsOfBusinessByUserId(@PathVariable("userUid") String userUid);
}
