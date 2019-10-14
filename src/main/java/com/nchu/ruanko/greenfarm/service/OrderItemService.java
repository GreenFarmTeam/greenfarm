package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.OrderItem;
import com.nchu.ruanko.greenfarm.pojo.vo.OrderItemVo;

import java.util.List;

public interface OrderItemService {
    List<OrderItemVo> loadCartOrderItem(String userUid);

    boolean deleteProductFromUserCart(String userUid, String productId);

    boolean alterProductNumFromUserCart(String userUid, String productId,int ProductNum);

    List<OrderItemVo> loadOrderItemsByOrderId(String orderId);
}
