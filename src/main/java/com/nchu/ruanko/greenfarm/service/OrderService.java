package com.nchu.ruanko.greenfarm.service;


import com.alipay.api.response.AlipayTradePagePayResponse;
import com.nchu.ruanko.greenfarm.pojo.entity.Order;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductReviewImage;
import com.nchu.ruanko.greenfarm.pojo.vo.BusinessOrderPageVo;

import java.util.List;

public interface OrderService {
    boolean addProductToCart(String productId,String userID);

    String userSubmitOrder(String userUid, String name, String phone, String addr, String detailAddr,String  totalPrice );

    List<Order> loadAllOrdersBy(String userUid);

    AlipayTradePagePayResponse payOrderByUserIdAndOrderId(String userUid, String orderId);

    String userPurchaseProduct(String userUid, String prdtId,String name, String phone, String addr, String detailAddr, String totalPrice,String prdNum);

    boolean confirmReceiveProducts(String orderId,String userId);
    boolean arrangeSendProducts(String orderId,String userId);

    AlipayTradePagePayResponse payFarmOrderByUserIdAndOrderId(String userUid, String orderFarmId);

    boolean confirmOrderFarms(String orderFarmId);


    BusinessOrderPageVo loadAllOrdersOfBusiness(String userUid, int pageNum, int pageSize, int pageNavigationSize);

    void saveOrderReviewPic(ProductReviewImage reviewImage, String orderId);

    void saveOrderReview(String productDegree, String wuLiuDegree, String remark, String orderId);
}
