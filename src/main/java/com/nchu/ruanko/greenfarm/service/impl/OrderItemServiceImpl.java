package com.nchu.ruanko.greenfarm.service.impl;

import com.github.pagehelper.PageHelper;
import com.nchu.ruanko.greenfarm.dao.OrderItemDAO;
import com.nchu.ruanko.greenfarm.dao.ProductDAO;
import com.nchu.ruanko.greenfarm.dao.ProductImageDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.OrderItem;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import com.nchu.ruanko.greenfarm.pojo.vo.OrderItemVo;
import com.nchu.ruanko.greenfarm.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemDAO orderItemDAO;
    @Autowired
    private ProductImageDAO productImageDAO;
    @Autowired
    private ProductDAO productDAO;


    /**
     * 加载用户购物车中的订单明细
     * @param userUid
     * @return
     */
    @Override
    public List<OrderItemVo> loadCartOrderItem(String userUid) {
        List<OrderItem> orderItemList = orderItemDAO.getCartOrderItemByuserUid(userUid);
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        for(OrderItem orderItem : orderItemList){
            OrderItemVo orderItemVo =new OrderItemVo();
           /* System.out.println("test:"+orderItem);*/
            ProductImage mainProductImage = productImageDAO.getProductMainImageByProductUID(orderItem.getProduct().getProductUid());
            if(mainProductImage==null){
                orderItemVo.setProductImage(new ProductImage());
            }else
                orderItemVo.setProductImage(mainProductImage);
            orderItemVo.setOrderItem(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        return orderItemVoList;
    }

    /**
     * 用户移除购物车中的某个产品
     * @param userUid
     * @param productId
     * @return
     */
    @Override
    public boolean deleteProductFromUserCart(String userUid, String productId) {
        if(orderItemDAO.deleteOrderItemByUserUidAndProductId(userUid,productId)>=1){
            return true;
        }else
            return false;
    }

    @Override
    public boolean alterProductNumFromUserCart(String userUid, String productId,int productNum) {

        Product product = productDAO.getProductByProductUID(productId);
        if(product.getProductStock()==null || productNum>product.getProductStock()){
            if(orderItemDAO.alterProductNumFromOrderItemByUserUidAndProductId(userUid,productId,productNum)>=1){
                orderItemDAO.updateOrderItemProductSumPriceByUserUidAndProductId(userUid,productId,productNum*product.getProductPrice());
                return true;
            }else
                return false;
        }else
            return false;


    }

    @Override
    public List<OrderItemVo> loadOrderItemsByOrderId(String orderId) {
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        List<OrderItem> orderItemList = orderItemDAO.loadOrderItemsByOrderId(orderId);

        for(OrderItem orderItem : orderItemList){
            OrderItemVo orderItemVo =new OrderItemVo();
            ProductImage mainProductImage = productImageDAO.getProductMainImageByProductUID(orderItem.getProduct().getProductUid());
            if(mainProductImage==null){
                orderItemVo.setProductImage(new ProductImage());
            }else
                orderItemVo.setProductImage(mainProductImage);
            orderItemVo.setOrderItem(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        return orderItemVoList;
    }
}
