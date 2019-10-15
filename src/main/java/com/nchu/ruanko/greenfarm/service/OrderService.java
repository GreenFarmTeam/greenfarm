package com.nchu.ruanko.greenfarm.service;


import com.alipay.api.response.AlipayTradePagePayResponse;
import com.nchu.ruanko.greenfarm.pojo.entity.Order;

import java.util.List;

public interface OrderService {
    boolean addProductToCart(String productId,String userID);

    String userSubmitOrder(String userUid, String name, String phone, String addr, String detailAddr,String  totalPrice );

    List<Order> loadAllOrdersBy(String userUid);

    AlipayTradePagePayResponse payOrderByUserIdAndOrderId(String userUid, String orderId);
}
