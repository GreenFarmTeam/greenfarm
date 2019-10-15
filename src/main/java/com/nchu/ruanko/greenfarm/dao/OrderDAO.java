package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderDAO {
    @Results(id = "orderMapper1", value = {
            @Result(property = "orderUid", column = "ord_uid"),
            @Result(property = "orderCtime", column = "ord_ctime"),
            @Result(property = "orderPtime", column = "ord_ptime"),
            @Result(property = "orderRname", column = "ord_rname"),
            @Result(property = "orderRaddress", column = "ord_raddr"),
            @Result(property = "orderRphone", column = "ord_phone"),
            @Result(property = "orderState", column = "ord_state"),
            @Result(property = "orderSum", column = "ord_sum"),
            @Result(property = "Member", column = "ord_user_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.UserDAO.getUserByUID"))
    })
    @Select("select * from gf_tb_order")
    List<Order> listAllOrders();


    @ResultMap(value = "orderMapper1")
    @Select(" select * from gf_tb_order " +
            " where ord_state=0 and ord_user_uid=#{userUid}")
    Order loadCartOrderDetailInfoByUserID(@Param("userUid") String userID);

    @ResultMap(value = "orderMapper1")
    @Select(" select * from gf_tb_order " +
            " where ord_uid=#{orderUid}")
    Order loadOneOrderByOrderID(@Param("orderUid") String orderID);
    /**
     * 创建购物车订单
     * @param uuid
     * @param productPrice
     * @param userID
     */
    @Insert("insert into gf_tb_order(ord_uid,ord_sum,ord_user_uid) values(#{uuid},#{productPrice},#{userID})")
    void createCartOrder(@Param("uuid") String uuid,@Param("productPrice") Float productPrice,@Param("userID") String userID);

    @Update("update gf_tb_order " +
            "set ord_state = #{state} " +
            "where ord_uid=#{orderUid} and ord_user_uid=#{userUid}")
    void updateOrderStateByUserIdAndOrderId(@Param("orderUid") String orderUid,@Param("userUid") String userUid,@Param("state") int unPayState);

    @Update("update gf_tb_order " +
            "set ord_rname = #{name}," +
            "ord_phone = #{phone}, " +
            "ord_raddr = #{address} " +
            "where ord_uid=#{orderUid} and ord_user_uid=#{userUid}")
    void updateOrderAddressInfoByUserIdAndOrderId(@Param("orderUid")String orderUid,@Param("userUid") String userUid,@Param("name") String name,@Param("phone") String phone,@Param("address") String address);

    @Update("update gf_tb_order " +
            "set ord_ctime = #{date} " +
            "where ord_uid=#{orderUid} and ord_user_uid=#{userUid}")
    void updateOrderCreateTimeByUserIdAndOrderId(@Param("orderUid")String orderUid,@Param("userUid") String userUid,@Param("date") Date date);

    @Update("update gf_tb_order " +
            "set ord_sum = #{totalPrice} " +
            "where ord_uid=#{orderUid} and ord_user_uid=#{userUid}")
    void updateOrderTotalPriceByUserIdAndOrderId(@Param("orderUid")String orderUid,@Param("userUid") String userUid,@Param("totalPrice") Float totalPrice);

    @ResultMap(value = "orderMapper1")
    @Select("select * from gf_tb_order " +
            "where ord_user_uid=#{userUid}")
    List<Order> listAllOrdersByUserid(@Param("userUid")String userUid);

    @Update("update gf_tb_order " +
            "set ord_ptime = #{date} " +
            "where ord_uid=#{orderId} and ord_user_uid=#{userUid}")
    void updateOrderPayTimeByUserIdAndOrderId(@Param("orderId") String orderId,@Param("userUid") String userUid,@Param("date") Date date);
}
