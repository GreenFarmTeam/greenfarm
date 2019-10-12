package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Order;
import org.apache.ibatis.annotations.*;

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
            " where ord_user_uid=#{orderUid}")
    Order loadOneOrderByOrderID(@Param("orderUid") String orderID);
    /**
     * 创建购物车订单
     * @param uuid
     * @param productPrice
     * @param userID
     */
    @Insert("insert into gf_tb_order(ord_uid,ord_sum,ord_user_uid) values(#{uuid},#{productPrice},#{userID})")
    void createCartOrder(@Param("uuid") String uuid,@Param("productPrice") Float productPrice,@Param("userID") String userID);
}
