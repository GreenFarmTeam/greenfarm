package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderRateDAO {
    @Results(id = "OrderRateMapper1", value = {
            @Result(property = "orderRateUid", column = "order_rate_uid"),
            @Result(property = "orderRateLogistics", column = "order_rate_logistics"),
            @Result(property = "orderRateProduct", column = "order_rate_product"),
            @Result(property = "orderRateremark", column = "order_rate_remark"),
            @Result(property = "orderRateOrderItem", column = "order_rate_orderItem", one = @One(select = "com.nchu.ruanko.greenfarm.dao.OrderItemDAO.getOrderItemById"))
    })
    @Select("select * from gf_tb_order_farm")
    List<Order> listAllOrderFarms();

}
