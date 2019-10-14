package com.nchu.ruanko.greenfarm.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.nchu.ruanko.greenfarm.config.AlipayConfig;
import com.nchu.ruanko.greenfarm.dao.OrderDAO;
import com.nchu.ruanko.greenfarm.dao.OrderItemDAO;
import com.nchu.ruanko.greenfarm.dao.ProductDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.Order;
import com.nchu.ruanko.greenfarm.pojo.entity.OrderItem;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.service.OrderService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderItemDAO orderItemDAO;
    @Autowired
    private ProductDAO productDAO;
    private static final int cartState = 0;
    private static final int unPayState = 1;
    private static final int delivery_waitProductState = 2;
    private static final int waitReceiveState = 3;
    private static final int ReceiveState = 4;



    /**
     * 添加商品到购物车
     * 逻辑：无在购物车中的订单，创建购物车订单：订单ID、总金额、买家编号。
     * 有：更新总金额
     *      查看购物车中是否已有该商品，无更新订单明细表：订单明细ID 条目单价 条目总价 订单UID 产品UID，
     *      有，不进行任何操作，返回False
     * @param productId
     * @param userID
     * @return
     */
    @Override
    public boolean addProductToCart(String productId,String userID) {
        

        Order order = orderDAO.loadCartOrderDetailInfoByUserID(userID);
        Product product = productDAO.getProductByProductUID(productId);

        if(order==null){
            String orderUID = StringUtils.createUUID();
            orderDAO.createCartOrder(orderUID,product.getProductPrice(),userID);
            orderItemDAO.insertCartOrderItem(StringUtils.createUUID(),product.getProductPrice(),product.getProductPrice(),orderUID,product.getProductUid());
        }else{
            orderDAO.updateOrderTotalPriceByUserIdAndOrderId(userID,order.getOrderUid(),order.getOrderSum()+product.getProductPrice());
            OrderItem orderItem = orderItemDAO.loadOrderItemDetailInfoByOrderIdAndProductId(order.getOrderUid(),product.getProductUid());
            /**当购物车中没有该商品时**/
            if(orderItem==null){
                orderItemDAO.insertCartOrderItem(StringUtils.createUUID(),product.getProductPrice(),product.getProductPrice(),order.getOrderUid(),product.getProductUid());
            }else
                return false;
        }
        return true;
    }

    /**
     * 用户提交购物车订单
     * 逻辑：更新用户订单状态：购物车->等待付款
     *       更新订单的收货人、收货人联系电话、收货地址、收货地址详情、创建时间、订单总金额
     * @param userUid
     * @param name
     * @param phone
     * @param addr
     * @param detailAddr
     * @return
     */
    @Override
    public boolean userSubmitOrder(String userUid, String name, String phone, String addr, String detailAddr,String totalPrice) {
        Order order = orderDAO.loadCartOrderDetailInfoByUserID(userUid);
        orderDAO.updateOrderStateByUserIdAndOrderId(order.getOrderUid(),userUid,unPayState);
        orderDAO.updateOrderAddressInfoByUserIdAndOrderId(order.getOrderUid(),userUid,name,phone,addr+"|"+detailAddr);
        orderDAO.updateOrderCreateTimeByUserIdAndOrderId(order.getOrderUid(),userUid,new Date(System.currentTimeMillis()));
        orderDAO.updateOrderTotalPriceByUserIdAndOrderId(order.getOrderUid(),userUid,Float.parseFloat(totalPrice));
        return true;
    }

    /**
     * 会员加载所有的订单，根据会员ID
     * @param userUid
     * @return
     */
    @Override
    public List<Order> loadAllOrdersBy(String userUid) {

        return orderDAO.listAllOrdersByUserid(userUid);
    }

    /**
     * 用户支付订单
     * @param userUid
     * @param orderId
     * @return
     */
    @Override
    public boolean payOrderByUserIdAndOrderId(String userUid, String orderId) {
        Order order =orderDAO.loadOneOrderByOrderID(orderId);
       List<OrderItem> orderItemList = orderItemDAO.loadOrderItemsByOrderId(order.getOrderUid());
        /* 生成订单号 */
        // 采取的策略是：“UUID”作为订单号
        String orderSn = UUID.randomUUID().toString().replaceAll("-", "");
        /* 请求支付 */
        // 初始化 AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        payRequest.setReturnUrl(AlipayConfig.return_url);
        payRequest.setNotifyUrl(AlipayConfig.notify_url);

        // 订单金额
        String totalAmount = String.valueOf(order.getOrderSum());

        // 订单名称
        String subject = "农产品清单";

        // 订单描述，可空
        String body = orderItemList.toString();

        payRequest.setBizContent("{\"out_trade_no\":\"" + orderSn + "\","
                + "\"total_amount\":\"" + totalAmount + "\"," + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        /* 响应 */
        AlipayTradePagePayResponse payResponse = null;

        try {
            payResponse = alipayClient.pageExecute(payRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (payResponse != null) {
            orderDAO.updateOrderStateByUserIdAndOrderId(userUid,orderId,delivery_waitProductState);
            return true;

        } else {
            return false;
        }
    }
}
