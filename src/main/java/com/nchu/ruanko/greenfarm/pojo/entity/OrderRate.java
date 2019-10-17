package com.nchu.ruanko.greenfarm.pojo.entity;

public class OrderRate {
    private String orderRateUid;
    private OrderItem orderRateOrderItem;
    private Integer orderRateLogistics;
    private Integer orderRateProduct;
    private String  orderRateremark;

    public String getOrderRateUid() {
        return orderRateUid;
    }

    public void setOrderRateUid(String orderRateUid) {
        this.orderRateUid = orderRateUid;
    }

    public OrderItem getOrderRateOrder() {
        return orderRateOrderItem;
    }

    public void setOrderRateOrder(OrderItem orderRateOrderItem) {
        this.orderRateOrderItem = orderRateOrderItem;
    }

    public Integer getOrderRateLogistics() {
        return orderRateLogistics;
    }

    public void setOrderRateLogistics(Integer orderRateLogistics) {
        this.orderRateLogistics = orderRateLogistics;
    }

    public Integer getOrderRateProduct() {
        return orderRateProduct;
    }

    public void setOrderRateProduct(Integer orderRateProduct) {
        this.orderRateProduct = orderRateProduct;
    }

    public String getOrderRateremark() {
        return orderRateremark;
    }

    public void setOrderRateremark(String orderRateremark) {
        this.orderRateremark = orderRateremark;
    }

    @Override
    public String toString() {
        return "OrderRate{" +
                "orderRateUid='" + orderRateUid + '\'' +
                ", orderRateOrder=" + orderRateOrderItem +
                ", orderRateLogistics=" + orderRateLogistics +
                ", orderRateProduct=" + orderRateProduct +
                ", orderRateremark='" + orderRateremark + '\'' +
                '}';
    }
}
