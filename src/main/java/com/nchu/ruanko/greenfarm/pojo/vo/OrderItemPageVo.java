package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.OrderItem;

public class OrderItemPageVo {
    private OrderItemVo orderItemVo;
    private PageInfo<OrderItem> pageInfo;

    public OrderItemVo getOrderItemVo() {
        return orderItemVo;
    }

    public void setOrderItemVo(OrderItemVo orderItemVo) {
        this.orderItemVo = orderItemVo;
    }

    public PageInfo<OrderItem> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<OrderItem> pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override
    public String toString() {
        return "OrderItemPageVo{" +
                "orderItemVo=" + orderItemVo +
                ", pageInfo=" + pageInfo +
                '}';
    }
}
