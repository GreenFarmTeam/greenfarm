package com.nchu.ruanko.greenfarm.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.config.AlipayConfig;
import com.nchu.ruanko.greenfarm.dao.*;
import com.nchu.ruanko.greenfarm.pojo.entity.*;
import com.nchu.ruanko.greenfarm.pojo.vo.BusinessOrderPageVo;
import com.nchu.ruanko.greenfarm.pojo.vo.BusinessOrderVo;
import com.nchu.ruanko.greenfarm.service.OrderService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
    @Autowired
    private OrderFarmDAO orderFarmDAO;
    @Autowired
    private FarmReviewImageDAO farmReviewImageDAO;
    @Autowired
    private ProductReviewImageDAO productReviewImageDAO;
    @Autowired
    private FarmDAO farmDAO;
    @Autowired
    private OrderRateDAO orderRateDAO;
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
     *       订单中所有的商品应减少相应的分量
     * @param userUid
     * @param name
     * @param phone
     * @param addr
     * @param detailAddr
     * @return
     */
    @Override
    public String userSubmitOrder(String userUid, String name, String phone, String addr, String detailAddr,String totalPrice) {
        Order order = orderDAO.loadCartOrderDetailInfoByUserID(userUid);
        orderDAO.updateOrderStateByUserIdAndOrderId(order.getOrderUid(),userUid,unPayState);
        orderDAO.updateOrderAddressInfoByUserIdAndOrderId(order.getOrderUid(),userUid,name,phone,addr+"|"+detailAddr);
        orderDAO.updateOrderCreateTimeByUserIdAndOrderId(order.getOrderUid(),userUid,new Date(System.currentTimeMillis()));
        orderDAO.updateOrderTotalPriceByUserIdAndOrderId(order.getOrderUid(),userUid,Float.parseFloat(totalPrice));
        List<OrderItem> orderItemList = orderItemDAO.loadOrderItemsByOrderId(order.getOrderUid());
        for(OrderItem orderItem   : orderItemList){
            Product product = productDAO.getProductByProductUID(orderItem.getProduct().getProductUid());

            if(!(product.getProductStock()==null)){//为空的库存充足,不用考虑
                Integer newStock = product.getProductStock()-orderItem.getItemCount();
                if(newStock<0){
                    return product.getProductName()+"已剩:"+product.getProductStock()+";库存不足！";
                }
            }

        }
        for(OrderItem orderItem   : orderItemList){
            Product product = productDAO.getProductByProductUID(orderItem.getProduct().getProductUid());
            if(!(product.getProductStock()==null)) {//为空的库存充足,不用考虑
                Integer newStock = product.getProductStock() - orderItem.getItemCount();
                productDAO.updateProductStockByProductUID(newStock >= 0 ? newStock : 0, product.getProductUid());
            }
            }
        return null;
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
    public AlipayTradePagePayResponse payOrderByUserIdAndOrderId(String userUid, String orderId) {
        Order order =orderDAO.loadOneOrderByOrderID(orderId);

       List<OrderItem> orderItemList = orderItemDAO.loadOrderItemsByOrderId(order.getOrderUid());
        /* 生成订单号 */
        // 采取的策略是：“UUID”作为订单号


        /* 请求支付 */
        // 初始化 AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        payRequest.setReturnUrl(AlipayConfig.return_url);
        payRequest.setNotifyUrl(AlipayConfig.notify_url);

        // 订单金额
        /*String totalAmount = String.valueOf(order.getOrderSum());*/

        String totalAmount = "180.00";
        // 订单名称
        String subject = "农产品清单";

       /* String orderSn = UUID.randomUUID().toString().replaceAll("-", "");*/
        // 订单描述，可空
        String body = "";
        for(OrderItem orderItem : orderItemList){
            body= body+orderItem.getProduct().getProductName()+" "+orderItem.getItemCount()+"  ￥"+orderItem.getItemPrice()+";";
        }


        DecimalFormat decimalFormat=new DecimalFormat(".00");
        String orderTotalPrice = decimalFormat.format(order.getOrderSum());
        payRequest.setBizContent("{\"out_trade_no\":\"" + order.getOrderUid() + "\","
                + "\"total_amount\":\"" +orderTotalPrice + "\"," + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

     /*   payRequest.setBizContent("{\"out_trade_no\":\"" + order.getOrderUid().replaceAll("-", "") + "\","
                + "\"total_amount\":\"" + totalAmount + "\"," + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");*/
        /* 响应 */
        AlipayTradePagePayResponse payResponse = null;

        try {
            payResponse = alipayClient.pageExecute(payRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (payResponse != null) {

            orderDAO.updateOrderStateByUserIdAndOrderId(orderId,userUid,delivery_waitProductState);
            orderDAO.updateOrderPayTimeByUserIdAndOrderId(orderId,userUid,new Date(System.currentTimeMillis()));
            return payResponse;

        } else {
            return null;
        }
    }

    /**
     * 用户直接购买商品，更新订单表除了支付时间已外的其他字段
     * 更新订单明细表：更新订单明细表全部,更新产品库存
     * @param userUid
     * @param name
     * @param phone
     * @param addr
     * @param detailAddr
     * @param totalPrice
     * @return
     */
    @Override
    public String userPurchaseProduct(String userUid,String prdtId, String name, String phone, String addr, String detailAddr, String totalPrice,String prdNum) {
         String orderId = StringUtils.createUUID();

         if(orderDAO.createOrder(orderId,new Date(System.currentTimeMillis()),name,phone,addr,totalPrice,unPayState,userUid)>=1){
            Product product = productDAO.getProductByProductUID(prdtId);
            if(orderItemDAO.createOrderItems(StringUtils.createUUID(),orderId,prdtId,product.getProductPrice(),totalPrice,Integer.parseInt(prdNum))>=1){

                /**更新库存**/
                Integer newStock = product.getProductStock()-Integer.parseInt(prdNum);
                if(newStock<0){
                    return product.getProductName()+"已剩:"+product.getProductStock()+";库存不足！";
                }else{
                    productDAO.updateProductStockByProductUID(newStock >= 0 ? newStock : 0, product.getProductUid());
                }
            }
        }else{
            return "数据库更新失败";
        }

        return null;
    }

    /**
     * 会员确认收货
     * @param orderId
     * @return
     */
    @Override
    public boolean confirmReceiveProducts(String orderId,String userId) {
        orderDAO.updateOrderStateByUserIdAndOrderId(orderId,userId,ReceiveState);
        return true;
    }
    /**
     *商家安排发货
     */
    @Override
    public boolean arrangeSendProducts(String orderId,String userId) {
        orderDAO.updateOrderStateByUserIdAndOrderId(orderId,userId,waitReceiveState);
        return true;
    }

    @Override
    public AlipayTradePagePayResponse payFarmOrderByUserIdAndOrderId(String userUid, String orderFarmId) {
        OrderFarm orderFarm = orderFarmDAO.selectOneOrderFarmInfoById(orderFarmId);

        /* 请求支付 */
        // 初始化 AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        payRequest.setReturnUrl(AlipayConfig.return_url);
        payRequest.setNotifyUrl(AlipayConfig.notify_url);

        // 订单金额
        /*String totalAmount = String.valueOf(order.getOrderSum());*/
        // 订单名称
        String subject = "农场预约清单";

        /* String orderSn = UUID.randomUUID().toString().replaceAll("-", "");*/
        // 订单描述，可空
        String body = ""+orderFarm.getOrderFarmFarm().getFarmName()+" 预约查看，费用：￥"+orderFarm.getOrderFarmFarm().getFarmPrice();

        DecimalFormat decimalFormat=new DecimalFormat(".00");
        String orderTotalPrice = decimalFormat.format(orderFarm.getOrderFarmFarm().getFarmPrice());
        payRequest.setBizContent("{\"out_trade_no\":\"" + orderFarm.getOrderFarmUid() + "\","
                + "\"total_amount\":\"" +orderTotalPrice + "\"," + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

     /*   payRequest.setBizContent("{\"out_trade_no\":\"" + order.getOrderUid().replaceAll("-", "") + "\","
                + "\"total_amount\":\"" + totalAmount + "\"," + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");*/
        /* 响应 */
        AlipayTradePagePayResponse payResponse = null;

        try {
            payResponse = alipayClient.pageExecute(payRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (payResponse != null) {
            orderFarmDAO.updateOrderFarmStateByOrderFarmId(orderFarmId,1);/**1代表已支付状态**/
            return payResponse;

        } else {
            return null;
        }
    }

    @Override
    public boolean confirmOrderFarms(String orderFarmId) {
        orderFarmDAO.updateOrderFarmStateByOrderFarmId(orderFarmId,2);
        return true;
    }


    /**
     * 加载商家所有的订单。即加载每个包含该商家的产品的用户订单。并且只加载属于该商家的订单子项
     * @param userUid
     * @return
     */
    @Override
    public BusinessOrderPageVo loadAllOrdersOfBusiness(String userUid, int pageNum, int pageSize, int pageNavigationSize) {

        List<Order> orderList = orderDAO.listAllBusinessOrdersByUserid(userUid);
        BusinessOrderPageVo businessOrderPageVo = new BusinessOrderPageVo();
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList, pageNavigationSize);
        List<BusinessOrderVo> businessOrderVoList = new ArrayList<>();
        for(Order order : orderList){
            BusinessOrderVo businessOrderVo = new BusinessOrderVo();
            businessOrderVo.setOrder(order);
            businessOrderVoList.add(businessOrderVo);
        }
        businessOrderPageVo.setBusinessOrderVoList(businessOrderVoList);
        businessOrderPageVo.setPageInfo(pageInfo);
        return businessOrderPageVo;
    }

    /**
     * 会员保存订单评论的图片
     * @param reviewImage
     * @param orderId
     */
    @Override
    public void saveOrderReviewPic(ProductReviewImage reviewImage, String orderId) {
        productReviewImageDAO.insertProductReviewImage(reviewImage.getProductReviewImageUid(),orderId,reviewImage.getProductReviewImagePath());
    }

    /**
     * 保存用户的订单评论内容
     * @param productDegree
     * @param wuLiuDegree
     * @param remark
     * @param orderId
     */
    @Override
    public void saveOrderReview(String productDegree, String wuLiuDegree, String remark, String orderId) {
        List<OrderItem> orderItemList = orderItemDAO.loadOrderItemsByOrderId(orderId);
        for(OrderItem orderItem : orderItemList){
            orderRateDAO.insertOrderRate(StringUtils.createUUID(),productDegree,wuLiuDegree,remark,orderId);
        }

    }
}
