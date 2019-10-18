package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Order;
import com.nchu.ruanko.greenfarm.pojo.entity.OrderRate;
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
    @Select("select * from gf_tb_order_rate")
    List<OrderRate> listAllOrderRates();

    @Insert("insert into gf_tb_order_rate values(#{uuid},#{orderItemId},#{productDegree},#{wuLiuDegree},#{remark})")
    void insertOrderRate(@Param("uuid") String uuid,@Param("productDegree") String productDegree,@Param("wuLiuDegree") String wuLiuDegree,@Param("remark") String remark,@Param("orderItemId") String orderId);

    @ResultMap(value="OrderRateMapper1")
    @Select("select * from gf_tb_order_rate " +
            "where order_rate_orderItem in(" +
            " select item_uid " +
            " from gf_tb_order_item " +
            " where item_product_uid=#{productId}" +
            ")")
    List<OrderRate> listAllOrderRatesByProductId(@Param("productId") String productId);

/*    @Insert("insert into gf_tb_order_rate values(#{},#{},#{},#{}) ")
    void insertOrderReview(String uuid, String productDegree, String wuLiuDegree, String remark, String orderId);*/
}
