package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderItemDAO {
    @Results(id = "orderItemMapper1", value = {
            @Result(property = "itemUid", column = "item_uid"),
            @Result(property = "itemPrice", column = "item_price"),
            @Result(property = "itemCount", column = "item_count"),
            @Result(property = "itemSum", column = "item_sum"),
            @Result(property = "order", column = "item_order_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.OrderDAO.loadOneOrderByOrderID")),
            @Result(property = "product", column = "item_product_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.ProductDAO.getProductByProductUID"))
    })
    @Select("select * from gf_tb_order_item")
    List<OrderItem> listAllOrderItems();

    @ResultMap(value="orderItemMapper1")
    @Select("select * from gf_tb_order_item " +
            "where item_uid= #{OrderItemId}")
    OrderItem getOrderItemById(@Param("orderItemId")String OrderItemId);

    @Insert("insert into gf_tb_order_item" +
            " values(#{uuid},#{productPrice},1,#{sumPrice},#{orderUID},#{productUid})")
    void insertCartOrderItem(@Param("uuid") String uuid,@Param("productPrice") Float productPrice,@Param("sumPrice") Float sumPrice,@Param("orderUID") String orderUID,@Param("productUid") String productUid);

    @ResultMap(value="orderItemMapper1")
    @Select("select * from gf_tb_order_item " +
            " where gf_tb_order_item.item_order_uid=#{orderUid}")
    List<OrderItem> loadOrderItemDetailInfoByOrderId(@Param("orderUid") String orderUid);

    @ResultMap(value="orderItemMapper1")
    @Select("select * from gf_tb_order_item " +
            " where gf_tb_order_item.item_order_uid=#{orderUid} and item_product_uid=#{productUid}")
    OrderItem loadOrderItemDetailInfoByOrderIdAndProductId(@Param("orderUid") String orderUid,@Param("productUid") String productUid);


    @ResultMap(value="orderItemMapper1")
    @Select("select * from gf_tb_order_item " +
            " where gf_tb_order_item.item_order_uid in (" +
            " select ord_uid from " +
            " gf_tb_order " +
            " where gf_tb_order.ord_user_uid=#{userUid} and gf_tb_order.ord_state=0 )")
    List<OrderItem> getCartOrderItemByuserUid(@Param("userUid") String userUid);

    @Delete("delete from gf_tb_order_item " +
            " where item_product_uid=#{productId} and gf_tb_order_item.item_order_uid in (" +
            " select ord_uid from " +
            " gf_tb_order    " +
            " where gf_tb_order.ord_user_uid=#{uid} and gf_tb_order.ord_state=0 )")
    int deleteOrderItemByUserUidAndProductId(@Param("uid") String userUID,@Param("productId") String productId);

    @Update("update gf_tb_order_item " +
            " set item_count=#{productNum} " +

            " where item_product_uid =#{productId} and gf_tb_order_item.item_order_uid in(" +
            " select ord_uid from " +
            " gf_tb_order    " +
            " where gf_tb_order.ord_user_uid=#{uid} and gf_tb_order.ord_state=0 )")
    int alterProductNumFromOrderItemByUserUidAndProductId(@Param("uid") String userUid,@Param("productId") String productId,@Param("productNum") int productNum);

    @Update("update gf_tb_order_item " +
            " set item_sum=#{itemSumPrice}" +
            " where item_product_uid =#{productId} and gf_tb_order_item.item_order_uid in(" +
            " select ord_uid from " +
            " gf_tb_order    " +
            " where gf_tb_order.ord_user_uid=#{uid} and gf_tb_order.ord_state=0 )")
    void updateOrderItemProductSumPriceByUserUidAndProductId(@Param("uid")String userUid,@Param("productId") String productId, @Param("itemSumPrice") Float itemSumPrice);

    @ResultMap(value="orderItemMapper1")
    @Select("select * from " +
            "gf_tb_order_item " +
            "where item_order_uid=#{orderId}")
    List<OrderItem> loadOrderItemsByOrderId(@Param("orderId") String orderId);

    @Insert("insert into gf_tb_order_item values(#{orderItemId},#{prdPrice},#{prdNum},#{totalPrice},#{orderId},#{prdtId})")
    int createOrderItems(@Param("orderItemId")String orderItemUid,@Param("orderId") String orderId,@Param("prdtId") String prdtId,@Param("prdPrice") Float prdPrice,@Param("totalPrice") String totalPrice,@Param("prdNum") int parseInt);
}
