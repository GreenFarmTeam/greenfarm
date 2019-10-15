package com.nchu.ruanko.greenfarm.service.impl;

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

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderItemDAO orderItemDAO;
    @Autowired
    private ProductDAO productDAO;


    /**
     * 添加商品到购物车
     * 逻辑：无在购物车中的订单，创建购物车订单：订单ID、总金额、买家编号。
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
            OrderItem orderItem = orderItemDAO.loadOrderItemDetailInfoByOrderIdAndProductId(order.getOrderUid(),product.getProductUid());
            /**当购物车中没有该商品时**/
            if(orderItem==null){
                orderItemDAO.insertCartOrderItem(StringUtils.createUUID(),product.getProductPrice(),product.getProductPrice(),order.getOrderUid(),product.getProductUid());
            }else
                return false;
        }
        return true;
    }
}
